package com.melikegoren.alarm.receiver

import android.app.Notification.MediaStyle
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioManager.RINGER_MODE_NORMAL
import android.media.AudioManager.STREAM_ALARM
import android.media.AudioManager.STREAM_MUSIC
import android.media.Ringtone
import android.media.RingtoneManager
import android.media.SoundPool
import android.os.Build
import androidx.core.app.NotificationCompat
import com.melikegoren.alarm.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {

        if(notificationManager.areNotificationsEnabled()){
            //Starting in Android 8.0 (API level 26), all notifications must be assigned to a channel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create the NotificationChannel
                //Notification channels allows user to customize notifications.

                val name = "Alarm"
                val descriptionText = "Alarm details"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val mChannel = NotificationChannel("AlarmId", name, importance)
                mChannel.description = descriptionText
                mChannel.enableVibration(true)
                mChannel.vibrationPattern = longArrayOf(0, 1000, 1000, 1000)
                notificationManager.createNotificationChannel(mChannel)
            }

            // Create the notification to be shown
            val mBuilder = NotificationCompat.Builder(context!!, "AlarmId")
                .setSmallIcon(R.drawable.notifications_icon)
                .setContentTitle("Alarm")
                .setContentText("Here\'s your alarm!")
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(AudioManager.STREAM_VOICE_CALL))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(longArrayOf(1000L))

            // Generate an Id for each notification
            val id = System.currentTimeMillis() / 1000

            //Show notification
            notificationManager.notify(id.toInt(), mBuilder.build())
        }

    }
}