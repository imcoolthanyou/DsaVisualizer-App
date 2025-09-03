package gautam.projects.dsavizualizer.sorting_techquines.presenatation.viewmodels

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gautam.projects.dsavizualizer.sorting_techquines.data.local.mergeSortWithHistory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MergeSortViewModel: ViewModel() {

    val _userList = MutableStateFlow<List<Int>>(emptyList())
    val userList=_userList.asStateFlow()

    var errorFlow = mutableListOf<String>()


    var _sortingSteps= MutableStateFlow<List<List<Int>>>(emptyList())
    var sortingSteps=_sortingSteps.asStateFlow()

    private val _currentStepIndex= MutableStateFlow(0)
    var currentStepIndex=_currentStepIndex.asStateFlow()

    var isSorting = MutableStateFlow<Boolean>(false)


    fun addNumber(number: Int){
        _userList.value=_userList.value+number
    }

    fun sampleNumber(){
        val list=List(5){(1..100).random()}
        _userList.value=list
    }

    fun startSort(){
        if(_userList.value.isEmpty())return

        isSorting.value=true

        val history = mergeSortWithHistory(_userList.value)
        _sortingSteps.value=history
        _currentStepIndex.value=0

        viewModelScope.launch {
            for (i in history.indices){
                _currentStepIndex.value=i
                delay(1500L)
            }
        }

    }
    fun reset(){
        _userList.value=emptyList()
        _sortingSteps.value=emptyList()
        _currentStepIndex.value=0
        isSorting.value=false

    }




}