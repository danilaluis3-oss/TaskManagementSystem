package com.example.todoapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.todoapp.data.room_database.TaskItem
import com.example.todoapp.ui.theme.darkGrey
import com.example.todoapp.ui.theme.grey


@Composable
fun ToDoItem(
    item: TaskItem,
    onEditClick: ()->Unit,
    onDeleteClick: ()-> Unit,
    onCheckChange: (Boolean)-> Unit
)
{

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(size = 20.dp),
        colors = CardDefaults.cardColors(
            if (item.isDone) Color.Gray else Color.White),
        border = BorderStroke(1.dp,grey)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {onCheckChange(!item.isDone)},
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector=
                        if (item.isDone) Icons.Filled.Circle
                        else Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint=if (item.isDone) Color.Green else grey
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text=item.dueDate,
                fontSize=12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text=item.taskName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
                color= if(item.isDone) grey else darkGrey,
                textDecoration =
                    if(item.isDone) TextDecoration.LineThrough
                    else null
            )

            Row{
                IconButton(
                    onClick = onEditClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit task",
                        tint=grey,
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete task",
                        tint=grey,
                        modifier = Modifier.size(16.dp)
                    )
                }

            }

        }
    }
}