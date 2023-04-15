package com.example.dnevnjak.presentation.viewModels

import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.data.repository.ObligationRepository
import com.example.dnevnjak.presentation.events.DnevnjakEvent
import com.example.dnevnjak.presentation.states.CalendarState
import com.example.dnevnjak.presentation.states.DailyPlanState
import com.example.dnevnjak.presentation.states.ObligationState
import com.example.dnevnjak.utilities.Priority
import com.example.dnevnjak.utilities.Utility
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.*


class MainViewModel(
    private val obligationRepository: ObligationRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _calendarState = MutableStateFlow(CalendarState())
    val calendarState = _calendarState.asStateFlow()

    private val _dailyPlanState = MutableStateFlow(DailyPlanState())
    val dailyPlanState = _dailyPlanState.asStateFlow()

    private val _obligationState = MutableStateFlow(ObligationState())
    val obligationState = _obligationState.asStateFlow()

    private val _priority = MutableStateFlow(Priority.High)


    init {
        GlobalScope.launch(Dispatchers.IO){
            initData()
            setStates()
        }
    }

    fun onEvent(event: DnevnjakEvent){
        when(event) {
            is DnevnjakEvent.SetHeaderDate -> {
                _calendarState.value = _calendarState.value.copy(
                    displayedYear = event.localDate.year,
                    displayedMonth = event.localDate.monthValue,
                    headerDate = event.localDate
                )
            }
            is DnevnjakEvent.ShowPastObligations -> {
                val show = !_dailyPlanState.value.showPastObligations
                when{
                    !show -> {
                        viewModelScope.launch {
                            var obligations = when (_dailyPlanState.value.filterByPriority) {
                                null -> {
                                    obligationRepository
                                        .getAllByDate(_dailyPlanState.value.date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
                                        .first()
                                }
                                else -> {
                                    obligationRepository
                                        .getAllByDateAndPriority(
                                            _dailyPlanState.value.date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
                                            _dailyPlanState.value.filterByPriority!!
                                        ).first()
                                }
                            }

                            val currDate = LocalDate.now()
                            val currTime = LocalTime.now()
                            obligations = obligations.filter {
                                    obligationEntity -> obligationEntity.date > currDate ||
                                    obligationEntity.date == currDate && obligationEntity.start > currTime
                            }

                            _dailyPlanState.value = _dailyPlanState.value.copy(
                                obligations = obligations,
                                showPastObligations = false
                            )
                        }
                    }
                    else -> {
                        viewModelScope.launch {
                            val obligations = when (_dailyPlanState.value.filterByPriority) {
                                null -> {
                                    obligationRepository
                                        .getAllByDate(_dailyPlanState.value.date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
                                        .first()
                                }
                                else -> {
                                    obligationRepository
                                        .getAllByDateAndPriority(
                                            _dailyPlanState.value.date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
                                            _dailyPlanState.value.filterByPriority!!
                                        ).first()
                                }
                            }
                            _dailyPlanState.value = _dailyPlanState.value.copy(
                                obligations = obligations,
                                showPastObligations = true
                            )
                        }
                    }
                }
            }
            is DnevnjakEvent.SetSearchQuery -> {
                _dailyPlanState.value = _dailyPlanState.value.copy( searchText = event.query )
                viewModelScope.launch {
                    val obligations = obligationRepository
                        .getAllByDateAndTitle(dailyPlanState.value.date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(), event.query)
                        .first()
                    _dailyPlanState.value = _dailyPlanState.value.copy(
                        obligations = obligations
                    )
                }
            }
            is DnevnjakEvent.FilterByPriority -> {
                _priority.value = event.priority
                viewModelScope.launch {
                    var obligations = obligationRepository
                        .getAllByDateAndPriority(dailyPlanState.value.date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(), event.priority)
                        .first()
                    val showPast = _dailyPlanState.value.showPastObligations
                    if(!showPast){
                        val currDate = LocalDate.now()
                        val currTime = LocalTime.now()
                        obligations = obligations.filter {
                                obligationEntity -> obligationEntity.date > currDate ||
                                obligationEntity.date == currDate && obligationEntity.start > currTime
                        }
                    }
                    _dailyPlanState.value = _dailyPlanState.value.copy(obligations = obligations)
                }
            }
            is DnevnjakEvent.DateTouched -> {
                viewModelScope.launch {
                    val obligations = obligationRepository
                        .getAllByDate(event.localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
                        .first()

                    _dailyPlanState.value = _dailyPlanState.value.copy(
                        obligations = obligations,
                        date = event.localDate,
                        showPastObligations = true
                    )
                }
            }
            DnevnjakEvent.SaveObligation -> {
                viewModelScope.launch {
                    obligationRepository.update(
                        ObligationEntity(
                            id = _obligationState.value.id,
                            date =  dailyPlanState.value.date,
                            priority = _obligationState.value.priority,
                            title = _obligationState.value.title,
                            description = _obligationState.value.description,
                            start = _obligationState.value.start,
                            end = _obligationState.value.end
                        )
                    )
                    resetObligationState()
                }
            }
            is DnevnjakEvent.SetDescription -> _obligationState.value = _obligationState.value.copy(description = event.description)
            is DnevnjakEvent.SetPriority -> _obligationState.value = _obligationState.value.copy(priority = event.priority)
            is DnevnjakEvent.SetStart -> _obligationState.value = _obligationState.value.copy(start = event.start)
            is DnevnjakEvent.SetEnd -> _obligationState.value = _obligationState.value.copy(end = event.end)
            is DnevnjakEvent.SetTitle ->_obligationState.value = _obligationState.value.copy(title = event.title )
            DnevnjakEvent.ShowAll -> { }
            is DnevnjakEvent.SelectedObligation -> {
                _obligationState.value.id = event.obligationEntity.id
                _obligationState.value.date = event.obligationEntity.date
                _obligationState.value.title = event.obligationEntity.title
                _obligationState.value.priority = event.obligationEntity.priority
                _obligationState.value.description = event.obligationEntity.description
                _obligationState.value.start = event.obligationEntity.start
                _obligationState.value.end = event.obligationEntity.end
                _obligationState.value.dateDiffFormatStr = Utility.fullDateFormatterStr(event.obligationEntity.date)
                _obligationState.value.obligationMode = "Review Obligation"
                setIsReviewingObligation()
            }
            DnevnjakEvent.CreateObligation -> {
                GlobalScope.launch {
                    obligationRepository.insert(
                        ObligationEntity(
                            date =  dailyPlanState.value.date,
                            priority = _obligationState.value.priority,
                            title = _obligationState.value.title,
                            description = _obligationState.value.description,
                            start = _obligationState.value.start,
                            end = _obligationState.value.end
                        )
                    )
                    setStates()
                    resetSingleObligationMode()
                }
            }
            DnevnjakEvent.EditObligation -> {
                setIsEditingObligation()
                _obligationState.value = _obligationState.value.copy(obligationMode = "Edit obligation")
            }
            DnevnjakEvent.DeleteObligation -> {
                setIsDeletingObligation()
                _obligationState.value = _obligationState.value.copy(obligationMode = "Delete obligation")
            }
            DnevnjakEvent.CancelObligation -> resetSingleObligationMode()
            DnevnjakEvent.DeleteObligationConfirmed -> resetSingleObligationMode()
            DnevnjakEvent.DeleteObligationCanceled -> setIsReviewingObligation()
            DnevnjakEvent.AddObligation -> setIsCreatingObligation()
        }
    }

    private fun setIsReviewingObligation(){
        _obligationState.value = _obligationState.value.copy(
                isReviewing = true,
                isAdding = false,
                isDeleting = false,
                isEditing = false,
                isSingleObligationMode = true
            )
    }
    private fun setIsCreatingObligation(){
        _obligationState.value = _obligationState.value.copy(
                isReviewing = false,
                isAdding = true,
                isDeleting = false,
                isEditing = false,
                isSingleObligationMode = true
        )
    }
    private fun setIsDeletingObligation(){
        _obligationState.value = _obligationState.value.copy(
            isReviewing = false,
            isAdding = false,
            isDeleting = true,
            isEditing = false,
            isSingleObligationMode = true
        )
    }
    private fun setIsEditingObligation(){
        _obligationState.value = _obligationState.value.copy(
            isReviewing = false,
            isAdding = false,
            isDeleting = false,
            isEditing = true,
            isSingleObligationMode = true
        )
    }

    private fun resetSingleObligationMode(){
        _obligationState.value = _obligationState.value.copy(
            isReviewing = false,
            isAdding = false,
            isDeleting = false,
            isEditing = false,
            isSingleObligationMode = false
        )
    }

    private fun resetObligationState(){
        _obligationState.value = ObligationState()
    }


    private suspend fun initData(){

        // initialize data if database is empty
        if(obligationRepository.getAll().first().isNotEmpty())
            return

        var localDate = LocalDate.now().minusDays(20)
        var priority = Priority.Low
        val title = "title"
        val description = "description"
        var start = LocalTime.now()
        var end = LocalTime.now().plusHours(1)

        val random = Random()
        for(i in 1..1000){
            val entity = ObligationEntity(
                date = localDate,
                priority = priority,
                title = title + i,
                description = description + i,
                start = start,
                end = end
            )
            priority = when(random.nextInt(100)){
                in 0..30 -> Priority.Low
                in 31..75 -> Priority.Mid
                else -> Priority.High
            }
            obligationRepository.insert(entity)

            start = start.plusHours(1)
            end = end.plusHours(1)

            if(i % (random.nextInt(7) + 1) == 0){
                localDate = localDate.plusDays(random.nextInt(3).toLong())
                start = LocalTime.now()
                end = LocalTime.now().plusHours(1)
            }
        }

    }

    private suspend fun setStates(){
        val colors = hashMapOf<LocalDate, Color>()
        val obligations = obligationRepository.getAll().first()
        obligations.forEach loop@{ (date, priority) ->

            if(colors[date] == null){
                colors[date] = when(priority){
                    Priority.High -> Color.Red
                    Priority.Mid -> Color.Yellow
                    Priority.Low -> Color.Green
                }
                return@loop
            }
            if(colors[date] == Color.Red || priority == Priority.High) {
                colors[date] = Color.Red
                return@loop
            }
            if(colors[date] == Color.Yellow || priority == Priority.Mid) {
                colors[date] = Color.Yellow
                return@loop
            }
            colors[date] = Color.Green
        }

        _calendarState.value = _calendarState.value.copy(
            obligations = obligations,
            colors = colors
        )
    }

}