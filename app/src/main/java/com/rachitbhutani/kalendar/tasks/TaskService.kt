package com.rachitbhutani.kalendar.tasks

import com.rachitbhutani.kalendar.tasks.data.AddNewTaskRequest
import com.rachitbhutani.kalendar.tasks.data.DeleteTaskRequest
import com.rachitbhutani.kalendar.tasks.data.GenericStatusResponse
import com.rachitbhutani.kalendar.tasks.data.TaskListResponse
import com.rachitbhutani.kalendar.tasks.data.TaskRequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskService {

    @POST("/api/getCalendarTaskList")
    suspend fun getAllTasks(@Body body: TaskRequestBody): TaskListResponse?

    @POST("/api/deleteCalendarTask")
    suspend fun deleteTask(@Body body: DeleteTaskRequest): GenericStatusResponse?

    @POST("/api/storeCalendarTask")
    suspend fun addNewTask(@Body body: AddNewTaskRequest): GenericStatusResponse?

}