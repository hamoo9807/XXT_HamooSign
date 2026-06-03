package com.cofbro.qian.wrapper.task.holder

import android.util.Log
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit


class TaskRunnableHolder {
    private companion object {
        const val TAG = "TaskRunnableHolder"
        const val THREAD_COUNT = 8
        const val TASK_TIMEOUT_SECONDS = 30L
    }

    private val workTasks = arrayListOf<Task>()
    private var resultFuture: List<Future<Result?>> = arrayListOf()
    private val result = arrayListOf<Result?>()
    private val threadWorker = Executors.newFixedThreadPool(THREAD_COUNT)

    private constructor()
    constructor(tasks: List<Task>) : this() {
        workTasks.addAll(tasks)
    }

    fun submit(): List<Future<Result?>> {
        resultFuture = threadWorker.invokeAll(workTasks, TASK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        return resultFuture
    }

    fun blockingFetchingResult(): List<Result?> {
        resultFuture.forEach { future ->
            try {
                result.add(future.get(TASK_TIMEOUT_SECONDS, TimeUnit.SECONDS))
            } catch (e: Exception) {
                Log.w(TAG, "Task timed out or failed", e)
                result.add(null)
            }
        }
        threadWorker.shutdown()
        return result
    }
}
