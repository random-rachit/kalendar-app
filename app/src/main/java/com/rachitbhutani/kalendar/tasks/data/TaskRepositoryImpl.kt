package com.rachitbhutani.kalendar.tasks.data

import com.rachitbhutani.kalendar.tasks.TaskService
import com.rachitbhutani.kalendar.util.Constants
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val service: TaskService
) : TaskRepository {

    override suspend fun getAllTasks(userId: Int) = service.getAllTasks(TaskRequestBody(userId))

    override suspend fun deleteTask(taskId: Int) =
        service.deleteTask(DeleteTaskRequest(Constants.USER_ID, taskId))

    override suspend fun addNewTask(title: String, desc: String) =
        service.addNewTask(AddNewTaskRequest(Constants.USER_ID, TaskDetail(title, desc)))

}