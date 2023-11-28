package com.melikegoren.alarm.domain.repository

import com.melikegoren.alarm.domain.entity.AlarmEntity
import com.melikegoren.alarm.domain.model.AlarmModel
import com.melikegoren.alarm.util.Resource
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    suspend fun getAllAlarms(): Flow<Resource<List<AlarmModel>>>

    suspend fun insertAlarm(alarm: AlarmEntity)

    suspend fun deleteAlarm(alarm: AlarmEntity)

    suspend fun getAlarmById(id: Int): AlarmEntity


}