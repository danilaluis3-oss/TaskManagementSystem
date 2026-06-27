package com.example.todoapp.data.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class], version=1)
abstract class TaskDatabase : RoomDatabase(){

    abstract fun taskDao() : TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context) : TaskDatabase{
            return INSTANCE?: synchronized(lock = this){
                Room.databaseBuilder(
                    context.applicationContext,
                    klass= TaskDatabase::class.java,
                    name="tasks_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}