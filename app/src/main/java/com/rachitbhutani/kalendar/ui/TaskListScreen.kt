package com.rachitbhutani.kalendar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rachitbhutani.kalendar.R
import com.rachitbhutani.kalendar.tasks.data.TaskData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskListScreen(
    modifier: Modifier,
    taskList: List<TaskData>,
    onDeleteClick: (taskId: Int) -> Unit
) {
    if (taskList.isEmpty()) {
        Text(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            text = "Click on the date\nAdd a task!",
            textAlign = TextAlign.Center,
            color = Color.Gray,
        )
        return
    }
    LazyColumn(
        modifier
            .animateContentSize()
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        stickyHeader {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp),
                text = stringResource(R.string.your_tasks),
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }
        items(taskList) {
            TaskItem(task = it, onDelete = onDeleteClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TaskItem(modifier: Modifier = Modifier, task: TaskData, onDelete: (taskId: Int) -> Unit) {

    var showDesc by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .background(SolidColor(Color.Blue), RoundedCornerShape(8.dp), 0.4f)
            .padding(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    showDesc = !showDesc
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = task.taskDetail?.title.orEmpty(), fontWeight = FontWeight.Bold)
            Icon(
                modifier = Modifier.clickable {
                    onDelete.invoke(task.taskId)
                },
                painter = painterResource(id = android.R.drawable.ic_delete),
                contentDescription = "Delete",
                tint = Color(
                    0xFFCE5454
                )
            )
        }
        AnimatedVisibility(visible = showDesc) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(modifier = Modifier.clickable {
                showDesc = !showDesc
            }, text = task.taskDetail?.description.orEmpty(), fontSize = 12.sp)
        }

    }
}
