package com.example.todoapp.notification

import android.app.NotificationManager
import android.os.Build
import androidx.core.app.ActivityCompat
import android.app.NotificationChannel
import android.Manifest
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.PeriodicWorkRequestBuilder
import android.app.Activity
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import androidx.work.ExistingPeriodicWorkPolicy
import android.content.Context
import android.util.Log




public fun periodicNotification(activity : Activity, context:Context) {

    Log.d("Notificari","Din PeriodicNotification")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1
        )
    }


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val channel = NotificationChannel(
            "task_reminders",
            "Task Reminders",
            NotificationManager.IMPORTANCE_HIGH
        )


        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    val workRequest =
        PeriodicWorkRequestBuilder<TaskReminderWorker>(
            15, TimeUnit.MINUTES
        )
            .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "task_reminder_work",
        ExistingPeriodicWorkPolicy.UPDATE,
        workRequest
    )

}