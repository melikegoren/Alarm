package com.melikegoren.alarm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.melikegoren.alarm.domain.entity.AlarmEntity

@Dao
interface AlarmDao {

    @Query("SELECT * FROM my_alarms")
    suspend fun getAllAlarms(): List<AlarmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: AlarmEntity)

    @Delete
    suspend fun deleteAlarm(alarm: AlarmEntity)

    @Query("SELECT * FROM my_alarms WHERE id = :id")
    suspend fun getAlarmById(id: Int): AlarmEntity
}