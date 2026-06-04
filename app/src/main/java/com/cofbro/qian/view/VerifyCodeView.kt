package com.cofbro.qian.view

import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.text.InputType
import android.util.AttributeSet
import androidx.core.widget.addTextChangedListener
import android.view.Gravity
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.cofbro.qian.R
import com.cofbro.qian.utils.dp2px
import java.lang.StringBuilder


class VerifyCodeView : ViewGroup {
    private var callback: ((String) -> Unit)? = null
    private var start = 0
    private var mSize = 0
    private val mSpace = dp2px(context, 10)
    private var mCurrentIndex = 0
    private val editList = arrayListOf<EditText>()

    companion object {
        private const val BOX_COUNT = 6
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initUi()
    }

    fun setCodeCallback(callback: (String) -> Unit) {
        this.callback = callback
    }

    private fun initUi() {
        for (i in 0 until BOX_COUNT) {
            val e = PasteAwareEditText(context, i).apply {
                if (Build.VERSION.SDK_INT >= 29) {
                    setTextCursorDrawable(R.drawable.cursor_color_shape)
                }
                gravity = Gravity.CENTER
                inputType = InputType.TYPE_CLASS_PHONE
                textSize = 56f  // ★ 放大字体 (原来35f太小, 6位验证码显示不全)

                addTextChangedListener(
                    afterTextChanged = {
                        it?.let { editable ->
                            val text = editable.toString().filter { ch -> ch.isDigit() }
                            if (text.isEmpty()) {
                                // 内容被清空, 可能是paste分发后置空, 忽略
                                return@addTextChangedListener
                            }
                            if (text.length > 1) {
                                // paste分发时这里可能收到多字符, 按第一位处理后截断
                                editable.clear()
                                editable.append(text.first())
                            }
                            if (editable.length == 1) {
                                if (mCurrentIndex == BOX_COUNT - 1) {
                                    callback?.let { it1 -> it1(getSMSCode()) }
                                }
                                nextFocus()
                            }
                        }
                    }
                )
                setOnKeyListener { v, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.action == MotionEvent.ACTION_DOWN) {
                        val editText = v as? EditText
                        if (editText?.text?.length == 0) {
                            previousFocus()
                            getCurrentEditText().text.clear()
                        }
                    }
                    false
                }
            }
            addView(e)
            editList.add(e)
        }
    }

    /**
     * ★ 自定义EditText: 拦截粘贴事件, 把粘贴的验证码分发到6个输入框
     */
    private inner class PasteAwareEditText(context: Context, private val boxIndex: Int) : EditText(context) {

        override fun onTextContextMenuItem(id: Int): Boolean {
            if (id == android.R.id.paste) {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                val pasteText = clipboard?.primaryClip?.getItemAt(0)?.text?.toString()
                    ?.filter { it.isDigit() } ?: ""
                if (pasteText.length > 1) {
                    // ★ 将粘贴的多位验证码分发到各个输入框
                    distributePastedText(pasteText, boxIndex)
                    return true
                }
                // 单字符粘贴走正常流程
            }
            return super.onTextContextMenuItem(id)
        }
    }

    /**
     * 将粘贴的数字串依次填入各个输入框
     */
    private fun distributePastedText(digits: String, startIndex: Int) {
        val chars = digits.take(BOX_COUNT).toCharArray()
        chars.forEachIndexed { i, ch ->
            val idx = startIndex + i
            if (idx < BOX_COUNT) {
                editList[idx].setText(ch.toString())
            }
        }
        // 焦点移到最后一个填了的框
        val lastIdx = (startIndex + chars.size - 1).coerceAtMost(BOX_COUNT - 1)
        mCurrentIndex = lastIdx
        editList[lastIdx].requestFocus()
        // 如果所有框都填满了, 触发回调
        if (getSMSCode().length >= BOX_COUNT) {
            callback?.let { it1 -> it1(getSMSCode()) }
        }
    }

    private fun getSMSCode(): String {
        val password = StringBuilder()
        editList.forEach {
            password.append(it.text)
        }
        return password.toString()
    }

    private fun previousFocus() {
        mCurrentIndex--
        if (mCurrentIndex >= 0) {
            editList[mCurrentIndex].requestFocus()
        } else {
            mCurrentIndex = 0
        }
    }

    private fun getCurrentEditText(): EditText {
        return editList[mCurrentIndex]
    }

    private fun nextFocus() {
        mCurrentIndex++
        if (mCurrentIndex < BOX_COUNT) {
            editList[mCurrentIndex].requestFocus()
        } else {
            mCurrentIndex = BOX_COUNT - 1
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mSize = (width - (BOX_COUNT - 1) * mSpace) / (BOX_COUNT + 2)
        start = (width - mSize * BOX_COUNT - mSpace * (BOX_COUNT - 1)) / 2
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        for (i in 0 until BOX_COUNT) {
            val v = getChildAt(i)
            val left = start + (mSize + mSpace) * i
            val right = left + mSize

            v.layout(left, 0, right, mSize + 10)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        postDelayed({
            editList.first().requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editList.first(), 0)
        }, 200)
    }
}
