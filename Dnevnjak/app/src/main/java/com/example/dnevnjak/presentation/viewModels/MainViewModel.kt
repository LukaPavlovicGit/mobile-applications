package com.example.dnevnjak.presentation.viewModels

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.data.repository.ObligationRepository
import com.example.dnevnjak.presentation.events.ObligationEvent
import com.example.dnevnjak.presentation.states.ObligationState
import com.example.dnevnjak.utilities.Priority
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class ObligationViewModel(
    private val obligationRepository: ObligationRepository
): ViewModel() {


    val headerDate = MutableStateFlow("")
    val state = MutableStateFlow(ObligationState())


    init{
        GlobalScope.launch(Dispatchers.IO){
            initData()
            initStates()
        }
    }

    fun onEvent(event: ObligationEvent){
        when(event){
            is ObligationEvent.SetHeaderDate -> {
                val localDate = event.localDate.format(dateFormatter)
                val tokens = localDate.toString().split("-")
                headerDate.value = tokens[1] + " " + tokens[2] + "."
            }


            else -> {

            }
        }
    }




    // initialize data if database is empty
    private suspend fun initData(){

        if(obligationRepository.getAll().first().isNotEmpty())
            return

        var localDate = LocalDate.now()
        var priority = Priority.Low
        val title = "title"
        val description = "description"
        var start = LocalDateTime.now()
        var end = LocalDateTime.now().plusHours(1)

        val random = Random()
        for(i in 1..200){

            val entity = ObligationEntity(
                date = localDate,
                priority = priority,
                title = title + i,
                description = description + i,
                start = start,
                end = end
            )

            priority =
                when(random.nextInt(100)){
                    in 0..30 -> Priority.Low
                    in 31..75 -> Priority.Mid
                    else -> Priority.High
                }

            start = start.plusHours(1)
            end = end.plusHours(1)

            obligationRepository.insert(entity)

            state.value.dayColorMap[localDate] = when(priority){
                Priority.High -> Color.Red
                Priority.Mid -> Color.Yellow
                Priority.Low -> Color.Green
            }

            if(i % (random.nextInt(2) + 1) == 0){
                localDate = localDate.plusDays(random.nextInt(3).toLong())
                start = LocalDateTime.now()
                end = LocalDateTime.now().plusHours(1)
            }
        }
    }

    private suspend fun initStates(){

        state.value.obligations = obligationRepository.getAll().first()
        state.value.obligations.forEach { (date, priority) ->
            state.value.dayColorMap[date] = when(priority){
                Priority.High -> Color.Red
                Priority.Mid -> Color.Yellow
                Priority.Low -> Color.Green
            }
        }

        val localDate = LocalDate.now().format(dateFormatter)
        val tokens = localDate.toString().split("-")
        headerDate.value = tokens[1] + " " + tokens[2] + "."
    }

    companion object{
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
    }
}