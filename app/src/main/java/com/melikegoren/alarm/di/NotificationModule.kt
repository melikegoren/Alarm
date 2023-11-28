package com.melikegoren.alarm.di

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.melikegoren.alarm.receiver.NotificationReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    fun providePendingIntentForNotificationReceiver(
        @ApplicationContext context: Context,
        notificationReceiver: NotificationReceiver
    ): PendingIntent {
        val broadcastIntent = Intent(context, notificationReceiver::class.java)
        return PendingIntent.getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_IMMUTABLE)
    }

    @Provides
    @Singleton
    fun provideNotificationReceiver(): NotificationReceiver {
        return NotificationReceiver()
    }
}