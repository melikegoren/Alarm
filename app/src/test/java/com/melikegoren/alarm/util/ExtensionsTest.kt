package com.melikegoren.alarm.util

import android.content.Context
import com.melikegoren.alarm.domain.entity.AlarmEntity
import com.melikegoren.alarm.domain.model.AlarmModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock

class ExtensionsTest {

    @Test
    fun alarmEntity_toAlarmModel() {
        // Arrange
        val alarmEntity = AlarmEntity(id = 1, hour = "10", minute = "30", days = listOf("Monday", "Wednesday"))

        // Act
        val alarmModel = alarmEntity.toAlarmModel()

        // Assert
        assertEquals(alarmEntity.id, alarmModel.id)
        assertEquals(alarmEntity.hour, alarmModel.hour)
        assertEquals(alarmEntity.minute, alarmModel.minute)
        assertEquals(alarmEntity.days, alarmModel.days)
    }

    @Test
    fun alarmModel_toAlarmEntity() {
        // Arrange
        val alarmModel = AlarmModel(id = 1, hour = "10", minute = "30", days = listOf("Monday", "Wednesday"))

        // Act
        val alarmEntity = alarmModel.toAlarmEntity()

        // Assert
        assertEquals(alarmModel.id, alarmEntity.id)
        assertEquals(alarmModel.hour, alarmEntity.hour)
        assertEquals(alarmModel.minute, alarmEntity.minute)
        assertEquals(alarmModel.days, alarmEntity.days)
    }





}