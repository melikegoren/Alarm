package com.melikegoren.alarm.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.melikegoren.alarm.receiver.NotificationReceiver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.text.SimpleDateFormat
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class AlarmHelperTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockAlarmManager: AlarmManager


    private lateinit var alarmHelper: AlarmHelper

    @Before
    fun setup() {
        mockContext = mock<Context>() //This gets the application context of the app being tested
        alarmHelper = AlarmHelper(mockAlarmManager, getPendingIntent())
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(mockContext, NotificationReceiver::class.java)
        return PendingIntent.getBroadcast(mockContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }


    @Test
    fun setAlarm_shouldSetAlarmIfCurrentTimeIsBeforeGivenTimeAndSelectedDay() {
        // Arrange
        val currentTime = System.currentTimeMillis()
        val futureTime = currentTime + 20000 // 20 seconds in the future
        val selectedDays = listOf(getCurrentDayOfWeek())

        // Act
        alarmHelper.setAlarm(mockContext, futureTime, selectedDays)

        // Assert
        verify(mockAlarmManager).set(
            AlarmManager.RTC_WAKEUP,
            futureTime,
            getPendingIntent()
        )
    }

    @Test
    fun setAlarm_shouldNotSetAlarmIfCurrentTimeIsAfterGivenTime(){
        // Arrange
        val currentTime = System.currentTimeMillis()
        val pastTime = currentTime - 20000 // 10 seconds in the past
        val selectedDays = listOf(getCurrentDayOfWeek())

        // Act
        alarmHelper.setAlarm(mockContext, pastTime, selectedDays)

        // Assert
        //set function shouldn't be called if it's called test will fail
        verify(mockAlarmManager, never()).set(
            anyInt(),
            anyLong(),
            any()
        )
    }

    @Test
    fun setAlarm_shouldNotSetAlarmIfCurrentDayIsNotSelectedDay() {
        // Arrange
        val currentTime = System.currentTimeMillis()
        val futureTime = currentTime + 10000 // 10 seconds in the future
        val nonMatchingDay = getNonMatchingDayOfWeek()
        val selectedDays = listOf(nonMatchingDay)

        // Act
        alarmHelper.setAlarm(mockContext, futureTime, selectedDays)

        // Assert
        verify(mockAlarmManager, never()).set(
            anyInt(),
            anyLong(),
            any()
        )
    }

    private fun getCurrentDayOfWeek(): String {
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(Calendar.getInstance().time)
    }

    private fun getNonMatchingDayOfWeek(): String {
        // Generate a non-matching day by adding or subtracting one day from the current day
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_WEEK, 1)
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)
    }
}
