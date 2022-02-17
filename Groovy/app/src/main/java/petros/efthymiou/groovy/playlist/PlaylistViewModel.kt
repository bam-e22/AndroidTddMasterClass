package petros.efthymiou.groovy.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData

class PlaylistViewModel(
    private val repository: PlaylistRepository
) : ViewModel() {
    val playlists: LiveData<Result<List<Playlist>>> = liveData {
        emitSource(repository.getPlayLists().asLiveData())
    }
}