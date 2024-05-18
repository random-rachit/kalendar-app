package com.rachitbhutani.kalendar.util

interface CalendarHeaderListener {

    fun onGridChange(forward: Boolean)

    fun onHeaderClick()

}

interface CalendarInteractionListener : CalendarHeaderListener {
    fun onDateSelected(date: Int)
}