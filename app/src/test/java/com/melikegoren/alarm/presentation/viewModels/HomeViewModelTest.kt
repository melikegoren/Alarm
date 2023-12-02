package com.melikegoren.alarm.presentation.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melikegoren.alarm.domain.entity.AlarmEntity
import com.melikegoren.alarm.domain.repository.AlarmRepository
import com.melikegoren.alarm.presentation.home.HomeUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest{

    @Mock
    private lateinit var repository: AlarmRepository

    lateinit var viewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)


    @ExperimentalCoroutinesApi
    @get:Rule
     val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(repository)
    }


    @Test
    fun `test getAllAlarms success`() = testScope.runTest {

        // Arrange
        val mockAlarms = listOf(AlarmEntity(1,"1","10", listOf("Monday", "Friday")),
            AlarmEntity(2,"2","5", listOf("Thursday"))
        )

        // Act
        viewModel.getAllAlarms()

        // Assert
        viewModel.alarmList.observeForever{ observedValue->
            assertEquals(HomeUiState.Success(mockAlarms), observedValue)
        }
    }

    @Test
    fun `test getAllAlarms error`() = testScope.runTest {

        // Arrange
        val errorMessage = "An error occurred"

        // Act
        viewModel.getAllAlarms()

        // Assert
        viewModel.alarmList.observeForever { observedValue ->
            assertEquals(HomeUiState.Error(errorMessage), observedValue)
        }
    }

    @Test
    fun `test getAllAlarms Loading`() = testScope.runTest{

        viewModel.getAllAlarms()

        viewModel.alarmList.observeForever { observedValue ->
            assertEquals(HomeUiState.Loading, observedValue)
        }

    }

    @Test
    fun `addAlarm should insert alarmEntity into repository`() = testScope.runTest {

        // Arrange
        val mockAlarmEntity = AlarmEntity(1,"1","10", listOf("Monday", "Friday"))

        // Act
        viewModel.addAlarm(mockAlarmEntity)

        // Verify
        verify(repository).insertAlarm(mockAlarmEntity)
    }

    @Test
    fun `deleteAlarm should delete alarmEntity from repository`() = testScope.runTest {
        // Arrange
        val mockAlarmEntity = AlarmEntity(1, "20", "20", listOf("Sunday", "Tuesday"))

        // Act
        viewModel.deleteAlarm(mockAlarmEntity)


        // Verify
       verify(repository).deleteAlarm(mockAlarmEntity)
    }
}