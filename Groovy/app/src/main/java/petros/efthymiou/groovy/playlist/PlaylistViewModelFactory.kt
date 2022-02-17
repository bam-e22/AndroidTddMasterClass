package petros.efthymiou.groovy.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlaylistViewModelFactory(
    private val playlistRepository: PlaylistRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistViewModel(playlistRepository) as T
    }
}