package com.example.kotlinworkmanager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val data = Data.Builder().putInt("value", 1).build()

        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .build()


        val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDB>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.id).observe(this) {
            workInfo ->
            when (workInfo.state) {
                WorkInfo.State.SUCCEEDED -> {
                    println("succeeded")
                }
                WorkInfo.State.FAILED -> {
                    println("failed")
                }
                WorkInfo.State.CANCELLED -> {
                    println("cancelled")
                }
                WorkInfo.State.RUNNING -> {
                    println("running")
                }

                else -> {
                    println("else")
                }
            }
        }

    }
}