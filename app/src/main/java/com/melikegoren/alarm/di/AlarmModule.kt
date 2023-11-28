package com.melikegoren.alarm.di

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import com.melikegoren.alarm.util.AlarmHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {

    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

   /* @Provides
    fun provideBootCompleteReceiver(): BootCompleteReceiver {
        return BootCompleteReceiver()
    }*/

    @Provides
    fun provideAlarmHelper(
        alarmManager: AlarmManager,
        pendingIntent: PendingIntent
    ): AlarmHelper {
        return AlarmHelper(alarmManager, pendingIntent)
    }


}