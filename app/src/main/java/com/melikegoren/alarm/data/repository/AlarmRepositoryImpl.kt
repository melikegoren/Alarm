package com.melikegoren.alarm.data.repository

import com.melikegoren.alarm.data.dao.AlarmDao
import com.melikegoren.alarm.di.IoDispatcher
import com.melikegoren.alarm.domain.entity.AlarmEntity
import com.melikegoren.alarm.domain.model.AlarmModel
import com.melikegoren.alarm.domain.repository.AlarmRepository
import com.melikegoren.alarm.util.Resource
import com.melikegoren.alarm.util.toAlarmModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher

) : AlarmRepository {
    override suspend fun getAllAlarms(): Flow<Resource<List<AlarmModel>>> = flow {

        emit(Resource.Loading)
        try {

                emit(Resource.Success(alarmDao.getAllAlarms().map { it.toAlarmModel() }))

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }

    }.flowOn(ioDispatcher)


    override suspend fun insertAlarm(alarm: AlarmEntity) {
        withContext(ioDispatcher) {
            alarmDao.insertAlarm(alarm)
        }
    }

    override suspend fun deleteAlarm(alarm: AlarmEntity) {
        withContext(ioDispatcher) {
            alarmDao.deleteAlarm(alarm)
        }
    }

    override suspend fun getAlarmById(id: Int): AlarmEntity =
        withContext(ioDispatcher) {
            alarmDao.getAlarmById(id)
        }


}