package com.rachitbhutani.kalendar.tasks.data

interface TaskRepository {

    suspend fun getAllTasks(userId: Int): TaskListResponse?

    suspend fun deleteTask(taskId: Int): GenericStatusResponse?

    suspend fun addNewTask(title: String, desc: String): GenericStatusResponse?


}