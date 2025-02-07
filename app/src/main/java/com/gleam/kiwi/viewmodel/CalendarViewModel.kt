package com.gleam.kiwi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.net.FetchResult
import com.gleam.kiwi.net.KiwiClient
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class CalendarViewModel(private val client: KiwiClient) : ViewModel() {
    private var taskList: Tasks? = null
    private var _daysContainTask: MutableLiveData<List<LocalDate>> = MutableLiveData()
    val daysContainTask: LiveData<List<LocalDate>>
        get() {
            return _daysContainTask
        }

    init {
        setTaskList()
    }

    private fun setTaskList() {
        viewModelScope.launch {
            val res = client.getTasks()
            taskList = when (res) {
                is FetchResult.Success -> {
                    res.result
                }
                else -> {
                    null
                }
            }
            setDaysContainTask()
        }
    }

    private fun setDaysContainTask() {
        _daysContainTask.value =
            taskList?.tasks?.map { t -> LocalDate.parse(t.date, DateTimeFormatter.ISO_DATE) }
    }

}