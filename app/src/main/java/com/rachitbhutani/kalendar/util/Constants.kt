package com.rachitbhutani.kalendar.util

object Constants {

    const val MIN_YEAR = 1970
    const val MAX_YEAR = 2100

    const val USER_ID = 667

    const val BASE_URL = "http://dev.frndapp.in:8085/"

    fun getDaysOfWeek(): List<String> {
        return listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    }

    fun getMonthsOfYear(): List<String> {
        return listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",)
    }

    fun getYearList() = (MIN_YEAR..MAX_YEAR).toList()

}