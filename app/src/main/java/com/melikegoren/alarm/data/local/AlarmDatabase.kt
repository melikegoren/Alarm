package com.melikegoren.alarm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.melikegoren.alarm.data.dao.AlarmDao
import com.melikegoren.alarm.domain.entity.AlarmEntity
import com.melikegoren.alarm.util.Converters

@Database(entities = [AlarmEntity::class], exportSchema = false, version = 1)
@TypeConverters(Converters::class)
abstract class AlarmDatabase: RoomDatabase() {

    abstract fun alarmDao(): AlarmDao
}