package com.melikegoren.alarm.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.melikegoren.alarm.util.Converters

@Entity(tableName = "my_alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hour: String,
    val minute: String,
    @TypeConverters(Converters::class)
    val days: List<String>
)