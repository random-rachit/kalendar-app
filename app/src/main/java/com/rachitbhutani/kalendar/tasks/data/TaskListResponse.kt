package com.rachitbhutani.kalendar.tasks.data

import com.google.gson.annotations.SerializedName

data class TaskRequestBody(
    @SerializedName("user_id")
    val userId: Int
)

data class TaskListResponse(
    val tasks: List<TaskData>?
)

data class TaskData(
    @SerializedName("task_id")
    val taskId: Int,
    @SerializedName("task_detail")
    val taskDetail: TaskDetail?
)

data class TaskDetail(
    val title: String?,
    val description: String?,
)

data class DeleteTaskRequest(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("task_id")
    val taskId: Int,
)

data class AddNewTaskRequest(
    @SerializedName("user_id")
    val userId: Int,
    val task: TaskDetail
)

data class GenericStatusResponse(
    val status: String?
) {
    companion object {
        const val Success = "Success"
    }
}