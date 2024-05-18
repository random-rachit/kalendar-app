package com.rachitbhutani.kalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rachitbhutani.kalendar.tasks.data.GenericStatusResponse
import com.rachitbhutani.kalendar.tasks.data.TaskData
import com.rachitbhutani.kalendar.tasks.data.TaskRepository
import com.rachitbhutani.kalendar.util.CalendarHandler
import com.rachitbhutani.kalendar.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val calendar: CalendarHandler,
    private val taskRepository: TaskRepository,
) : ViewModel() {

    val calendarState = calendar.calendarState

    val addTaskBsState = MutableStateFlow(false)
    val dateChangeBsState = MutableStateFlow(false)

    private val _taskListState = MutableStateFlow<List<TaskData>>(emptyList())
    val taskList: StateFlow<List<TaskData>> by lazy { _taskListState }

    init {
        fetchTasks()
    }

    fun goToMonth(month: Int, year: Int) {
        calendar.updateCalendar(month, year)
    }

    fun navigateMonth(forward: Boolean) {
        val month = calendarState.value.get(Calendar.MONTH)
        val year = calendarState.value.get(Calendar.YEAR)

        val (newMonth, newYear) =
            if (forward)
                if (month == Calendar.DECEMBER) Calendar.JANUARY to year + 1
                else (month + 1 to year)
            else
                if (month == Calendar.JANUARY) Calendar.DECEMBER to year - 1
                else (month - 1 to year)

        calendar.updateCalendar(newMonth, newYear)
    }

    fun goToToday() {
        calendar.goToToday()
    }

    private fun fetchTasks() = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.getAllTasks(Constants.USER_ID)?.let { tasks ->
            _taskListState.value = tasks.tasks.orEmpty()
        }
    }

    fun deleteTask(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.deleteTask(id)?.takeIf { it.status == GenericStatusResponse.Success }.let {
            fetchTasks()
        }
    }

    fun addNewTask(title: String, desc: String) = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.addNewTask(title, desc)?.let {
            fetchTasks()
        }
    }

    fun showAddTaskBS(show: Boolean) {
        addTaskBsState.value = show
    }

    fun showDateChangeBs(show: Boolean) {
        dateChangeBsState.value = show
    }

}