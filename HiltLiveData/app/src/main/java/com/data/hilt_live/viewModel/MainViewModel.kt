package com.data.hilt_live.viewModel

import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.data.hilt_live.model.ImageResponse
import com.data.hilt_live.repository.MainRepository
import com.data.hilt_live.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(
//    photoDao: PhotoDao,
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<ImageResponse>> = MutableLiveData()

    val dataState: LiveData<DataState<ImageResponse>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent, query: String) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetPhotoEvents -> {
                    mainRepository.getPhoto(query)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.None -> {
                    // No action
                }
            }
        }
    }

}

sealed class MainStateEvent {
    object GetPhotoEvents : MainStateEvent()
    object None : MainStateEvent()
}

