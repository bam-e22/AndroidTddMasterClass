package petros.efthymiou.groovy.playlist

import androidx.lifecycle.*
import kotlinx.coroutines.flow.onEach

class PlaylistViewModel(
    private val repository: PlaylistRepository
) : ViewModel() {
    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> = _loader

    val playlists: LiveData<Result<List<Playlist>>> = liveData {
        _loader.postValue(true)
        emitSource(repository.getPlayLists()
            .onEach {
                _loader.postValue(false)
            }
            .asLiveData()
        )
    }
}