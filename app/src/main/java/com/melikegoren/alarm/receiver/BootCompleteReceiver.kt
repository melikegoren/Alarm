package com.melikegoren.alarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.melikegoren.alarm.domain.repository.AlarmRepository
import com.melikegoren.alarm.util.AlarmHelper
import com.melikegoren.alarm.util.Resource
import com.melikegoren.alarm.util.convertTimeToMilliseconds
import com.melikegoren.alarm.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootCompleteReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmRepository: AlarmRepository

    @Inject
    lateinit var alarmHelper: AlarmHelper
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                alarmRepository.getAllAlarms().collect {
                    when (it) {
                        is Resource.Loading -> {}
                        is Resource.Error -> {
                            context!!.showToast("Error collecting alarms.")
                        }
                        is Resource.Success -> {

                            for(item in it.result!!){
                                val timeInMilli =
                                    convertTimeToMilliseconds(item.hour.toInt(), item.minute.toInt())
                                alarmHelper.setAlarm(context!!,timeInMilli,item.days)

                            }
                        }

                    }
                }
            }
        }

    }



}