package petros.efthymiou.groovy.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlaylistDetailViewModel(
    private val service: PlaylistDetailService
) : ViewModel() {
    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> = _loader

    private val _playlistDetail = MutableLiveData<Result<PlaylistDetail>>()
    val playlistDetail: LiveData<Result<PlaylistDetail>> = _playlistDetail

    fun getPlaylistDetail(id: String) {
        viewModelScope.launch {
            _loader.postValue(true)
            service.fetchPlaylistDetail(id)
                .onEach {
                    _loader.postValue(false)
                }
                .collect {
                    _playlistDetail.postValue(it)
                }
        }
    }
}