package com.example.todoapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.todoapp.ui.screens.ToDoListScreen
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.viewmodel.TaskViewModel
import com.example.todoapp.viewmodel.TaskViewModelFactory
import com.example.todoapp.notification.AlarmScheduler.scheduleDailyAlarm
import com.example.todoapp.notification.TaskReminderWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoapp.notification.periodicNotification


class MainActivity : ComponentActivity() {

    private val videoModel: TaskViewModel by viewModels {
        TaskViewModelFactory(application)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Notifications
        //scheduleDailyAlarm(this)
        //Sa afiseze
/*
        val workRequest =
            OneTimeWorkRequestBuilder<TaskReminderWorker>()
                .build()

        WorkManager.getInstance(this).enqueue(workRequest)
*/
        //
        periodicNotification(this,this)

        //
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                ToDoListScreen(videoModel)
            }
        }
    }
//

//
}
