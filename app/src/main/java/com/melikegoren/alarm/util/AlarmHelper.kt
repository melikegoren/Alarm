package com.melikegoren.alarm.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

 class AlarmHelper @Inject constructor(
    internal val alarmManager: AlarmManager,
    internal val pendingIntent: PendingIntent

){
    fun setAlarm(context: Context, timeOfAlarm: Long, selectedDays: List<String>) {
        val calendar = Calendar.getInstance()
        val currentDayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)

        if (System.currentTimeMillis() < timeOfAlarm && selectedDays.contains(currentDayOfWeek)) {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                timeOfAlarm,
                pendingIntent
            )
        }
    }

}


