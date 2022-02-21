package petros.efthymiou.groovy.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaylistDetailViewModel(
    private val service: PlaylistDetailService
) : ViewModel() {
    private val _playlistDetail = MutableLiveData<Result<PlaylistDetail>>()
    val playlistDetail: LiveData<Result<PlaylistDetail>> = _playlistDetail

    fun getPlaylistDetail(id: String) {
        viewModelScope.launch {
            service.fetchPlaylistDetail(id)
                .collect {
                    _playlistDetail.postValue(it)
                }
        }
    }
}