package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow

class PlaylistRepository(
    private val service: PlaylistService
) {
    suspend fun getPlayLists(): Flow<Result<List<Playlist>>> {
        return service.fetchPlaylists()
    }
}