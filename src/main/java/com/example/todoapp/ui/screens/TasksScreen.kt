package com.example.todoapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import com.example.todoapp.data.room_database.TaskItem
import com.example.todoapp.ui.theme.darkGrey
import com.example.todoapp.ui.theme.grey
import com.example.todoapp.viewmodel.TaskViewModel




@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun ToDoListScreen(viewModel: TaskViewModel){

    val tasks by viewModel.allTasks.collectAsState()

    var taskToEdit by remember { mutableStateOf<TaskItem?>(value=null) }
    var showEditorDialog by remember { mutableStateOf(value=false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    taskToEdit=null
                    showEditorDialog=true
                },
                shape = RoundedCornerShape(size = 20.dp),
                containerColor = darkGrey,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",

                )

                Text(
                    text = "Add Task",
                    modifier=Modifier.padding(8.dp)
                )

            }
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 24.dp)
        ){
            Text(
                text = "My Tasks",
                modifier = Modifier.padding(top=32.dp),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = darkGrey
            )

            Text(
                text = "${tasks.filter { !it.isDone }.size} remaining today",
                color= grey
            )

            if(tasks.isEmpty()){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text="No tasks",
                        color=grey
                    )
                }

            }
            else{
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                )
                {
                    items(
                        items=tasks,
                        key={it.id}
                    ){
                        task->
                        ToDoItem(
                            item = task,
                            onEditClick = {
                                taskToEdit=task
                                showEditorDialog=true
                            },
                            onDeleteClick ={viewModel.deleteTask(task)},
                            onCheckChange = {checked->viewModel.updateTask(task.copy(isDone = checked))}
                        )
                    }
                }
            }
            }
    }

    if(showEditorDialog){
        TaskEditorDialog(
            task=taskToEdit,
            { newName,dateTask->
                if(taskToEdit==null){
                    viewModel.addTask(TaskItem(taskName = newName , isDone = false,dueDate=dateTask))
                }else{
                    tasks.find { it.id == taskToEdit!!.id }?.let { currentTask-> viewModel.updateTask(currentTask.copy(taskName=newName)) }
                }
                showEditorDialog=false
                taskToEdit=null
            },
            {
                showEditorDialog=false
                taskToEdit=null
            }
        )
    }

}