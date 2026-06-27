package com.example.todoapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.todoapp.data.room_database.TaskItem

import androidx.compose.foundation.layout.Row
import androidx.compose.ui.platform.LocalContext



import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import kotlin.text.format

import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePicker

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun TaskEditorDialog(
    task: TaskItem?,
    onSave: (String,String)-> Unit,
    onCancel: ()-> Unit
){

    var showDialog by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf(task?.dueDate ?:"") }
    var taskName by remember { mutableStateOf(task?.taskName ?:"") }


    ModalBottomSheet(
        onDismissRequest = onCancel,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding()
        ){
            Text(
                text=if (task==null) "Create new task" else "Update task",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text= selectedDate
                    )
                Button(
                    onClick = {showDialog=true},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .padding(horizontal = 25.dp
                        )
                    ,
                    shape = RoundedCornerShape(size = 12.dp)

                ) {
                    Text(
                        text = "Alege data",
                        fontSize = 14.sp
                    )
                }

            }

            Spacer(modifier = Modifier.height(24.dp))


            OutlinedTextField(
                value = taskName,
                onValueChange = {taskName=it},
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {Text(text="Add new task")}
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onSave(taskName.trim(),selectedDate) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = taskName.isNotBlank()
            ){
                Text(
                    text="Save task",
                    fontSize = 16.sp
                )
            }
        }

        //
        if (showDialog) {

            val datePickerState = rememberDatePickerState()

            DatePickerDialog(
                onDismissRequest = {
                    showDialog = false
                },

                confirmButton = {

                    TextButton(
                        onClick = {

                            val millis =
                                datePickerState.selectedDateMillis

                            if (millis != null) {

                                val formatter = SimpleDateFormat(
                                    "dd/MM/yyyy",
                                    Locale.getDefault()
                                )

                                selectedDate =
                                    formatter.format(Date(millis))
                            }

                            showDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {

                DatePicker(
                    state = datePickerState
                )
            }
        }

        //
    }
}
