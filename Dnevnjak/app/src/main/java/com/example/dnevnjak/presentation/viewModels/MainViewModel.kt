package com.example.dnevnjak.presentation.viewModels

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
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


class MainViewModel(
    private val obligationRepository: ObligationRepository
): ViewModel() {

    private val _headerFullDate = MutableStateFlow("")
    val headerFullDate = _headerFullDate.asStateFlow()

    private val _showPastObligations = MutableStateFlow(true)
    val showPastObligations = _showPastObligations.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _obligationsByDate = MutableStateFlow(emptyList<ObligationEntity>())
    val obligationsByDate = _obligationsByDate.asStateFlow()

    private val _filteredList = MutableStateFlow(emptyList<ObligationEntity>())
    val filteredList = _filteredList.asStateFlow()

    private val _allObligations = MutableStateFlow(emptyList<ObligationEntity>())
    val allObligations = _allObligations.asStateFlow()

    private val _dayColorMap = MutableStateFlow(hashMapOf<LocalDate, Color>())
    val dayColorMap = _dayColorMap.asStateFlow()

    private val _displayedMonth = MutableStateFlow(1);
    val displayedMonth = _displayedMonth.asStateFlow()

    private val _displayedYear = MutableStateFlow(2023);
    val displayedYear = _displayedYear.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    private val _obligationState = MutableStateFlow(ObligationState())
    val obligationState = _obligationState.asStateFlow()

    private val _headerDate = MutableStateFlow("")
    val headerDate = _headerDate.asStateFlow()

    private val _selectedObligation = MutableStateFlow(ObligationState())
    val selectedObligation = _selectedObligation.asStateFlow()

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
            initStates()
        }
    }

    fun onEvent(event: ObligationEvent){
        when(event){
            is ObligationEvent.SetHeaderDate -> {
                _headerDate.value =  dateDiffFormatStr(event.localDate)
                _displayedMonth.value = event.localDate.monthValue
                _displayedYear.value = event.localDate.year
            }
            is ObligationEvent.ShowPastObligations -> {
                _showPastObligations.value = !showPastObligations.value
            }
            is ObligationEvent.SetSearchQuery -> {
                _searchText.value = event.query
            }
            is ObligationEvent.FilterByPriority -> {
                _filteredList.value = obligationsByDate.value.filter { obligationEntity -> obligationEntity.priority == event.priority }
            }
            is ObligationEvent.DateTouched -> {
                _headerFullDate.value = fullDateDiffFormatStr(event.localDate)
                _obligationsByDate.value = _allObligations.value.filter { obligationEntity -> obligationEntity.date == event.localDate }
            }
            is ObligationEvent.DeleteObligation -> TODO()
            ObligationEvent.EditObligation -> TODO()
            is ObligationEvent.FilterObligations -> TODO()
            is ObligationEvent.HideConfirmationDialog -> TODO()
            ObligationEvent.HideDialog -> {
                _isReviewingObligation.value = false
                _isAddingObligation.value = false
                _isDeletingObligation.value = false
                _isEditingObligation.value = false
            }
            ObligationEvent.SaveObligation -> TODO()
            is ObligationEvent.SetDescription -> TODO()
            is ObligationEvent.SetEnd -> TODO()
            is ObligationEvent.SetHeaderFullDate -> TODO()
            is ObligationEvent.SetPriority -> TODO()
            is ObligationEvent.SetStart -> TODO()
            is ObligationEvent.SetTitle -> TODO()
            ObligationEvent.ShowAll -> TODO()
            ObligationEvent.ShowConfirmationDialog -> TODO()
            ObligationEvent.ShowNewObligationScreen -> { }
            is ObligationEvent.SelectedObligation -> {
                _selectedObligation.value.date = event.obligationEntity.date
                _selectedObligation.value.title = event.obligationEntity.title
                _selectedObligation.value.priority = event.obligationEntity.priority
                _selectedObligation.value.description = event.obligationEntity.description
                _selectedObligation.value.start = event.obligationEntity.start
                _selectedObligation.value.end = event.obligationEntity.end
                _selectedObligation.value.dateDiffFormatStr = fullDateDiffFormatStr(event.obligationEntity.date)
                setIsReviewingObligation()
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
                start = LocalDateTime.now()
                end = LocalDateTime.now().plusHours(1)
            }
        }
    }

    private suspend fun initStates(){

        Log.e("TAG", "USAO?!")

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

        val localDate = LocalDate.now().format(dateFormatter)
        val tokens = localDate.toString().split("-")
        _headerDate.value =  tokens[1] + " " + tokens[2] + "."
        _headerFullDate.value = tokens[1] + " " + tokens[0] + ". " + tokens[2] + "."

        _displayedMonth.value = LocalDate.now().monthValue
        _displayedYear.value = LocalDate.now().year
    }

    private fun setIsReviewingObligation(){
        _selectedObligation.value.isReviewingObligation = true
        _selectedObligation.value.isAddingObligation = false
        _selectedObligation.value.isDeletingObligation = false
        _selectedObligation.value.isEditingObligation = false

        _isReviewingObligation.value = true
        _isAddingObligation.value = false
        _isDeletingObligation.value = false
        _isEditingObligation.value = false
    }
    private fun setIsAddingObligation(){
        _selectedObligation.value.isReviewingObligation = false
        _selectedObligation.value.isAddingObligation = true
        _selectedObligation.value.isDeletingObligation = false
        _selectedObligation.value.isEditingObligation = false

        _isReviewingObligation.value = false
        _isAddingObligation.value = true
        _isDeletingObligation.value = false
        _isEditingObligation.value = false
    }
    private fun setIsDeletingObligation(){
        _selectedObligation.value.isReviewingObligation = false
        _selectedObligation.value.isAddingObligation = false
        _selectedObligation.value.isDeletingObligation = true
        _selectedObligation.value.isEditingObligation = false

        _isReviewingObligation.value = false
        _isAddingObligation.value = false
        _isDeletingObligation.value = true
        _isEditingObligation.value = false
    }
    private fun setIsEditingObligation(){
        _selectedObligation.value.isReviewingObligation = false
        _selectedObligation.value.isAddingObligation = false
        _selectedObligation.value.isDeletingObligation = false
        _selectedObligation.value.isEditingObligation = true

        _isReviewingObligation.value = false
        _isAddingObligation.value = false
        _isDeletingObligation.value = false
        _isEditingObligation.value = true
    }

    private fun fullDateDiffFormatStr(date: LocalDate): String {
        val localDate = date.format(dateFormatter)
        val tokens = localDate.toString().split("-")
        return tokens[1] + " " + tokens[0] + ". " + tokens[2] + "."
    }

    private fun dateDiffFormatStr(date: LocalDate): String {
        val localDate = date.format(dateFormatter)
        val tokens = localDate.toString().split("-")
        return tokens[1] + " " + tokens[2] + "."
    }

    companion object{
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
    }
}