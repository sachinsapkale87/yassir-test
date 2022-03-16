package com.sachin.test.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: HomeRepository
) : ViewModel() {

    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable.localizedMessage)
    }


    private val _stateflow = MutableStateFlow<PageAPiuiState>(PageAPiuiState.Empty)
    val pageUiStateFlow: StateFlow<PageAPiuiState> = _stateflow

    sealed class PageAPiuiState {
        data class Success(val list: List<PageObj.Movie>) : PageAPiuiState()
        data class SuccessImage(val movieObj: PageObj.Movie) : PageAPiuiState()
        data class Error(val msg: String) : PageAPiuiState()
        object Loading : PageAPiuiState()
        object Empty : PageAPiuiState()
    }

    fun getMovieListUsingStaeFlow() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _stateflow.value = PageAPiuiState.Loading
            val data = repository.getMoviesList()
            withContext(Dispatchers.Main) {
                if (data.isSuccessful) {
                    _stateflow.value = PageAPiuiState.Success(data!!.body()!!.resultList)
                } else {
                    _stateflow.value = PageAPiuiState.Error("Empty List")
                }
            }
        }

    }

    fun getMovieDetailsUsingStateFlow(id: Int) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _stateflow.value = PageAPiuiState.Loading
            val data = repository.getMovieDetails(id)
            withContext(Dispatchers.Main) {
                if (data.isSuccessful) {
                    _stateflow.value = PageAPiuiState.SuccessImage(data!!.body()!!)
                } else {
                    _stateflow.value = PageAPiuiState.Error("Something went wrong")
                }
            }
        }
    }

    fun onError(msg: String) {
        _stateflow.value = PageAPiuiState.Error(msg)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}