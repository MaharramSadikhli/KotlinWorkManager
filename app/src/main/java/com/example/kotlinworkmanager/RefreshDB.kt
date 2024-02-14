package com.example.kotlinworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshDB(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val value = inputData.getInt("value", 0)
        refreshDB(value)
        return Result.success()
    }

    private fun refreshDB(value: Int) {
        val sharedPreferences = context.getSharedPreferences("com.example.kotlinworkmanager", Context.MODE_PRIVATE)
        var num = sharedPreferences.getInt("num", 0)
        num += value
        println(num)
        sharedPreferences.edit().putInt("num", num).apply()
    }
}