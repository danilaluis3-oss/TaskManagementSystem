package com.example.todoapp.notification

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.Context
import com.example.todoapp.data.room_database.TaskDatabase
import kotlinx.coroutines.Dispatchers
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.todoapp.data.room_database.TaskItem
import java.time.LocalDate
import kotlinx.coroutines.CoroutineScope
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.launch
import com.example.todoapp.notification.AlarmScheduler.scheduleDailyAlarm


class TaskReminderReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("Worker", "Alarma pentru Receiver ")
        val database = TaskDatabase.getDatabase(context)
        val taskDao = database.taskDao()

        CoroutineScope(Dispatchers.IO).launch {

            val tomorrow = LocalDate.now()
                .plusDays(1)
                .toString()

            val tasks = taskDao.getTasksForDate(tomorrow)

            tasks.forEach { task ->
                showNotification(context, task)
            }
            scheduleDailyAlarm(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(context: Context, task: TaskItem) {

        val channelId = "task_reminders"

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Task Reminders",
            NotificationManager.IMPORTANCE_HIGH
        )

        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Task Reminder")
            .setContentText("Task '${task.taskName}' expires tomorrow")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .build()

        manager.notify(task.id, notification)
    }
}