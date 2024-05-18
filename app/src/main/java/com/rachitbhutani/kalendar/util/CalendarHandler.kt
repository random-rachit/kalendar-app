package com.rachitbhutani.kalendar.util

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarHandler @Inject constructor() {

    private val calendar = Calendar.getInstance(Locale.getDefault())

    private val _currentState = MutableStateFlow(calendar)
    val calendarState by lazy { _currentState }

    fun updateCalendar(month: Int, year: Int) {
        val clone = calendar.clone() as Calendar
        clone.set(Calendar.YEAR, year)
        clone.set(Calendar.MONTH, month)
        calendarState.value = clone
    }

    fun goToToday() {
        calendarState.value = calendar
    }

}

fun Calendar.getMonthName(): String? = getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

fun Calendar.getStartDayOfMonth(): Int {
    val clone = this.clone() as Calendar
    clone.set(Calendar.DAY_OF_MONTH, 0)
    return clone.get(Calendar.DAY_OF_WEEK)
}

fun Calendar.getDaysInMonth() = getActualMaximum(Calendar.DAY_OF_MONTH)