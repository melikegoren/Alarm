package com.melikegoren.alarm.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melikegoren.alarm.domain.entity.AlarmEntity
import com.melikegoren.alarm.domain.model.AlarmModel
import com.melikegoren.alarm.domain.repository.AlarmRepository
import com.melikegoren.alarm.presentation.home.HomeUiState
import com.melikegoren.alarm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
) : ViewModel() {

    private var _alarmList = MutableLiveData<HomeUiState<List<AlarmModel>>>()
    val alarmList: LiveData<HomeUiState<List<AlarmModel>>> get() = _alarmList


    private val _daysToAdd = MutableLiveData<List<String>>()
    val daysToAdd: LiveData<List<String>> get() = _daysToAdd


    init {
        getAllAlarms()
    }

    fun toggleCheckbox(text: String, isChecked: Boolean) {
        val currentList = _daysToAdd.value.orEmpty().toMutableList()

        if (isChecked) {
            currentList.add(text)
        } else {
            currentList.removeAll { it == text }
        }

        _daysToAdd.value = currentList
    }

    fun getAllAlarms() {
        viewModelScope.launch {
            alarmRepository.getAllAlarms().collectLatest {
                when (it) {
                    is Resource.Loading -> _alarmList.value = HomeUiState.Loading
                    is Resource.Error -> _alarmList.value =
                        HomeUiState.Error(it.exception.toString())

                    is Resource.Success -> _alarmList.value = HomeUiState.Success(it.result!!)

                }
            }
        }
    }

    fun addAlarm(alarmEntity: AlarmEntity) {
        viewModelScope.launch {
            alarmRepository.insertAlarm(alarmEntity)
        }

    }

    fun deleteAlarm(alarmEntity: AlarmEntity) {
        viewModelScope.launch {
            alarmRepository.deleteAlarm(alarmEntity)
        }
    }

}