package com.example.dnevnjak.presentation.viewModels

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.data.repository.ObligationRepository
import com.example.dnevnjak.presentation.events.ObligationEvent
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

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    private val _priority = MutableStateFlow(Priority.High)

    private val _showPastObligations = MutableStateFlow(true)
    val showPastObligations = _showPastObligations.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _obligationsByDate = MutableStateFlow(emptyList<ObligationEntity>())
    val obligationsByDate = _obligationsByDate.asStateFlow()

    private val _allObligations = MutableStateFlow(emptyList<ObligationEntity>())
    val allObligations = _allObligations.asStateFlow()

    private val _dayColorMap = MutableStateFlow(hashMapOf<LocalDate, Color>())
    val dayColorMap = _dayColorMap.asStateFlow()

    private val _displayedMonth = MutableStateFlow(LocalDate.now().monthValue);
    val displayedMonth = _displayedMonth.asStateFlow()

    private val _displayedYear = MutableStateFlow(LocalDate.now().year);
    val displayedYear = _displayedYear.asStateFlow()

    private val _newObligationState = MutableStateFlow(ObligationState())
    val newObligationState = _newObligationState.asStateFlow()

    private val _headerDate = MutableStateFlow(LocalDate.now())
    val headerDate = _headerDate.asStateFlow()

    private val _obligationState = MutableStateFlow(ObligationState())
    val obligationState = _obligationState.asStateFlow()

    // singleObligationMode needs to be combination of isRevOb, IsAddOb, isEditOb and idDelOb
    private var _singleObligationMode = MutableStateFlow(false)
    var singleObligationMode = _singleObligationMode.asStateFlow()

    private var _isReviewingObligation = MutableStateFlow(false)
    var isReviewingObligation = _isReviewingObligation.asStateFlow()

    private var _isAddingObligation = MutableStateFlow(false)
    var isAddingObligation = _isAddingObligation.asStateFlow()

    private var _isEditingObligation = MutableStateFlow(false)
    var isEditingObligation = _isEditingObligation.asStateFlow()

    private var _isDeletingObligation = MutableStateFlow(false)
    var isDeletingObligation = _isDeletingObligation.asStateFlow()


    init {
        GlobalScope.launch(Dispatchers.IO){
            initData()
            setStates()
        }
    }

    fun onEvent(event: ObligationEvent){
        when(event) {
            is ObligationEvent.SetHeaderDate -> {
                _headerDate.value = event.localDate
                _displayedMonth.value = event.localDate.monthValue
                _displayedYear.value = event.localDate.year
            }
            is ObligationEvent.ShowPastObligations -> {
                _showPastObligations.value = !showPastObligations.value
                when{
                    !_showPastObligations.value -> {
                        viewModelScope.launch {
                            val time = LocalTime.now().toSecondOfDay().toLong()
                            _obligationsByDate.value = obligationRepository
                                .getAllByDateAndTime(selectedDate.value.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(), time)
                                .first()
                        }
                    }
                    else -> {
                        viewModelScope.launch {
                            _obligationsByDate.value = obligationRepository
                                .getAllByDate(selectedDate.value.atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
                                .first()
                        }
                    }
                }
            }
            is ObligationEvent.SetSearchQuery -> {
                _searchText.value = event.query
                viewModelScope.launch {
                    _obligationsByDate.value = obligationRepository
                        .getAllByDateAndTitle(selectedDate.value.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(), event.query)
                        .first()
                }
            }
            is ObligationEvent.FilterByPriority -> {
                _priority.value = event.priority
                viewModelScope.launch {
                    _obligationsByDate.value = obligationRepository
                        .getAllByDateAndPriority(selectedDate.value.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(), event.priority)
                        .first()
                }
            }
            is ObligationEvent.DateTouched -> {
                _selectedDate.value = event.localDate
                viewModelScope.launch {
                    _obligationsByDate.value = obligationRepository
                        .getAllByDate(event.localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
                        .first()
                }
            }
            ObligationEvent.SaveObligation -> {
                viewModelScope.launch {
                    obligationRepository.update(
                        ObligationEntity(
                            id = _obligationState.value.id,
                            date =  _selectedDate.value,
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
            is ObligationEvent.SetDescription -> _obligationState.value = _obligationState.value.copy(description = event.description)
            is ObligationEvent.SetPriority -> _obligationState.value = _obligationState.value.copy(priority = event.priority)
            is ObligationEvent.SetStart -> _obligationState.value = _obligationState.value.copy(start = event.start)
            is ObligationEvent.SetEnd -> _obligationState.value = _obligationState.value.copy(end = event.end)
            is ObligationEvent.SetTitle ->_obligationState.value = _obligationState.value.copy(title = event.title )
            ObligationEvent.ShowAll -> { }
            is ObligationEvent.SelectedObligation -> {
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
            ObligationEvent.CreateObligation -> {
                GlobalScope.launch {
                    obligationRepository.insert(
                        ObligationEntity(
                            date =  _selectedDate.value,
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
            ObligationEvent.EditObligation -> setIsEditingObligation()
            ObligationEvent.DeleteObligation -> setIsDeletingObligation()
            ObligationEvent.CancelObligation -> resetSingleObligationMode()
            ObligationEvent.DeleteObligationConfirmed -> resetSingleObligationMode()
            ObligationEvent.DeleteObligationCanceled -> setIsReviewingObligation()
            ObligationEvent.AddObligation -> setIsCreatingObligation()
        }
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
            priority =
                when(random.nextInt(100)){
                    in 0..30 -> Priority.Low
                    in 31..75 -> Priority.Mid
                    else -> Priority.High
                }

            start = start.plusHours(1)
            end = end.plusHours(1)

            obligationRepository.insert(entity)

            _dayColorMap.value[localDate] = when(priority){
                Priority.High -> Color.Red
                Priority.Mid -> Color.Yellow
                Priority.Low -> Color.Green
            }

            if(i % (random.nextInt(7) + 1) == 0){
                localDate = localDate.plusDays(random.nextInt(3).toLong())
                start = LocalTime.now()
                end = LocalTime.now().plusHours(1)
            }
        }
    }

    private suspend fun setStates(){
        _allObligations.value = obligationRepository.getAll().first()
        _allObligations.value.forEach loop@{ (date, priority) ->

            if(_dayColorMap.value[date] == null){
                _dayColorMap.value[date] = when(priority){
                    Priority.High -> Color.Red
                    Priority.Mid -> Color.Yellow
                    Priority.Low -> Color.Green
                }
                return@loop
            }
            if(_dayColorMap.value[date] == Color.Red || priority == Priority.High) {
                _dayColorMap.value[date] = Color.Red
                return@loop
            }
            if(_dayColorMap.value[date] == Color.Yellow || priority == Priority.Mid) {
                _dayColorMap.value[date] = Color.Yellow
                return@loop
            }

            _dayColorMap.value[date] = Color.Green
        }
    }

    private fun setIsReviewingObligation(){
        _singleObligationMode.value = true

        _isReviewingObligation.value = true
        _isAddingObligation.value = false
        _isDeletingObligation.value = false
        _isEditingObligation.value = false
    }
    private fun setIsCreatingObligation(){
        _singleObligationMode.value = true

        _isReviewingObligation.value = false
        _isAddingObligation.value = true
        _isDeletingObligation.value = false
        _isEditingObligation.value = false
    }
    private fun setIsDeletingObligation(){
        _singleObligationMode.value = true

        _isReviewingObligation.value = false
        _isAddingObligation.value = false
        _isDeletingObligation.value = true
        _isEditingObligation.value = false
    }
    private fun setIsEditingObligation(){
        _singleObligationMode.value = true

        _isReviewingObligation.value = false
        _isAddingObligation.value = false
        _isDeletingObligation.value = false
        _isEditingObligation.value = true
    }

    private fun resetSingleObligationMode(){
        _singleObligationMode.value = false
        _isReviewingObligation.value = false
        _isAddingObligation.value = false
        _isDeletingObligation.value = false
        _isEditingObligation.value = false
    }

    private fun resetObligationState(){
        _obligationState.value = ObligationState()
    }


}