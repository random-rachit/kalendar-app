package com.rachitbhutani.kalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rachitbhutani.kalendar.ui.AddTaskBottomSheet
import com.rachitbhutani.kalendar.ui.CalendarBox
import com.rachitbhutani.kalendar.ui.DateChangeBottomSheet
import com.rachitbhutani.kalendar.ui.TaskListScreen
import com.rachitbhutani.kalendar.ui.theme.KalendarTheme
import com.rachitbhutani.kalendar.util.CalendarInteractionListener
import com.rachitbhutani.kalendar.util.Constants
import com.rachitbhutani.kalendar.util.getDaysInMonth
import com.rachitbhutani.kalendar.util.getMonthName
import com.rachitbhutani.kalendar.util.getStartDayOfMonth
import com.rachitbhutani.kalendar.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalendarTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    val showAddTaskBS by viewModel.addTaskBsState.collectAsState()
                    val showDateChangeBS by viewModel.dateChangeBsState.collectAsState()
                    val calendarState by viewModel.calendarState.collectAsState()

                    fun canNavigate(forward: Boolean): Boolean {
                        val year = calendarState.get(Calendar.YEAR)
                        return if ((forward && year == Constants.MAX_YEAR) || (forward.not() && year == Constants.MIN_YEAR)) {
                            showToast(getString(R.string.calendar_limit_reached))
                            false
                        } else true
                    }

                    Column {
                        val taskList by viewModel.taskList.collectAsState()

                        CalendarBox(
                            Modifier
                                .padding(it)
                                .weight(1f),
                            calendarState.getStartDayOfMonth(),
                            calendarState.getDaysInMonth(),
                            calendarState.getMonthName().orEmpty(),
                            calendarState.get(Calendar.YEAR).toString(),
                            object : CalendarInteractionListener {
                                override fun onDateSelected(date: Int) {
                                    viewModel.showAddTaskBS(true)
                                }

                                override fun onGridChange(forward: Boolean) {
                                    if (canNavigate(forward))
                                        viewModel.navigateMonth(forward)
                                }

                                override fun onHeaderClick() {
                                    viewModel.showDateChangeBs(true)
                                }

                            }
                        )
                        TaskListScreen(
                            Modifier
                                .background(
                                    Color.White,
                                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                )
                                .border(
                                    3.dp,
                                    Color.Gray,
                                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                )
                                .weight(0.6f),
                            taskList
                        ) { id ->
                            viewModel.deleteTask(id)
                        }
                    }

                    if (showAddTaskBS) {
                        AddTaskBottomSheet(onDone = { title, desc ->
                            viewModel.addNewTask(title, desc)
                            viewModel.showAddTaskBS(false)
                        }, onDismiss = {
                            viewModel.showAddTaskBS(false)
                        })
                    }

                    if (showDateChangeBS) {
                        DateChangeBottomSheet(onDone = { month, year ->
                            if (month == -1 || year == -1)
                                viewModel.goToToday()
                            else viewModel.goToMonth(month, year)
                            viewModel.showDateChangeBs(false)
                        }, onDismiss = { month, year ->
                            viewModel.goToMonth(month, year)
                            viewModel.showDateChangeBs(false)
                        }, currentMonth = calendarState.get(Calendar.MONTH),
                            currentYear = calendarState.get(Calendar.YEAR)
                        )
                    }
                }
            }
        }
    }
}
