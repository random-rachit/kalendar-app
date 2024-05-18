package com.rachitbhutani.kalendar.tasks.data

import com.rachitbhutani.kalendar.util.Constants

interface TaskRepository {

    suspend fun getAllTasks(userId: Int): TaskListResponse?

    suspend fun deleteTask(taskId: Int): GenericStatusResponse?

    suspend fun addNewTask(title: String): GenericStatusResponse?


}