package com.melikegoren.alarm.domain.model

data class AlarmModel(
    val id: Int,
    val hour: String,
    val minute: String,
    val days: List<String> = listOf()
)