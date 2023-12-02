package com.melikegoren.alarm.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

class HelperFunctionsTest {

    @Test
    fun convertTimeToMilliseconds_validTime_returnsCorrectMilliseconds() {
        // Arrange
        val expectedHour = 12
        val expectedMinute = 30

        // Act
        val result = convertTimeToMilliseconds(expectedHour, expectedMinute)

        // Assert
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, expectedHour)
            set(Calendar.MINUTE, expectedMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        assertEquals(calendar.timeInMillis, result)
    }

    @Test
    fun convertTimeToMilliseconds_zeroTime_returnsCorrectMilliseconds() {
        // Arrange
        val expectedHour = 0
        val expectedMinute = 0

        // Act
        val result = convertTimeToMilliseconds(expectedHour, expectedMinute)

        // Assert
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, expectedHour)
            set(Calendar.MINUTE, expectedMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        assertEquals(calendar.timeInMillis, result)
    }
}