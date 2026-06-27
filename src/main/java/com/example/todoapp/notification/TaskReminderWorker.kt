package com.example.todoapp.notification

import android.content.Context
import java.time.LocalDate
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.todoapp.data.room_database.TaskDatabase
import android.app.NotificationChannel
import androidx.core.app.NotificationCompat
import com.example.todoapp.data.room_database.TaskItem
import androidx.work.WorkerParameters
import androidx.work.CoroutineWorker
import kotlinx.coroutines.flow.toList
import android.util.Log

class TaskReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {

        val database = TaskDatabase.getDatabase(applicationContext)
        val taskDao = database.taskDao()
        val tomorrow = LocalDate.now().plusDays(1).toString()

        val tasks = taskDao.getTasksForDate(tomorrow)
        Log.d("Worker", "Alarma pentru "+tomorrow)

        tasks.forEach { task ->
            showNotification(task)
        }

        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(task: TaskItem) {

        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        val channelId = "task_reminders"

        val channel = NotificationChannel(
            channelId,
            "Task Reminders",
            NotificationManager.IMPORTANCE_HIGH
        )

        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Task Reminder")
            .setContentText(
                "Task '${task.taskName}' expires tomorrow"
            )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        manager.notify(task.id, notification)
    }
}