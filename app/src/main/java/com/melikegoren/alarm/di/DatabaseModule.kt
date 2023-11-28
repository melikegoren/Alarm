package com.melikegoren.alarm.di

import android.content.Context
import androidx.room.Room
import com.melikegoren.alarm.data.dao.AlarmDao
import com.melikegoren.alarm.data.local.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAlarmDao(alarmDatabase: AlarmDatabase): AlarmDao {
        return alarmDatabase.alarmDao()
    }

    @Provides
    @Singleton
    fun provideAlarmDatabase(@ApplicationContext appContext: Context): AlarmDatabase {
        return Room.databaseBuilder(
            appContext,
            AlarmDatabase::class.java,
            "AlarmDatabaseBuilder"
        ).build()
    }
}