package com.melikegoren.alarm.util

import android.content.Context
import android.widget.Toast
import com.melikegoren.alarm.domain.entity.AlarmEntity
import com.melikegoren.alarm.domain.model.AlarmModel

fun AlarmEntity.toAlarmModel(): AlarmModel{

    return AlarmModel(
        id = this.id,
        hour = this.hour,
        minute = this.minute,
        days = this.days
    )
}

fun AlarmModel.toAlarmEntity(): AlarmEntity{
    return AlarmEntity(
        id = this.id,
        hour = this.hour,
        minute = this.minute,
        days = this.days
    )
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


