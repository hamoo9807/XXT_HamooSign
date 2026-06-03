package com.cofbro.qian

import com.cofbro.qian.utils.AmapKeyStore
import org.junit.Assert.*
import org.junit.Test

class AmapKeyStoreTest {

    @Test
    fun testKeyDecryption() {
        val key = AmapKeyStore.getApiKey()
        // 验证Key长度为32位(MD5格式)
        assertEquals("Key长度应为32", 32, key.length)
        // 验证Key内容正确
        assertEquals("127bbda580dde9856103d6f2a7c9cb9d", key)
    }

    @Test
    fun testKeyIsHexFormat() {
        val key = AmapKeyStore.getApiKey()
        // 验证Key全部为十六进制字符
        assertTrue("Key应为十六进制格式", key.all { it in '0'..'9' || it in 'a'..'f' })
    }

    @Test
    fun testKeyCache() {
        val key1 = AmapKeyStore.getApiKey()
        val key2 = AmapKeyStore.getApiKey()
        // 验证缓存生效,两次返回相同引用
        assertSame("Key应被缓存", key1, key2)
    }

    @Test
    fun testKeyNotPlainText() {
        val key = AmapKeyStore.getApiKey()
        // 验证不是旧Key
        assertNotEquals("不应为旧Key", "797648bd1ff4e06dd433af6e4f3b87fc", key)
    }
}
