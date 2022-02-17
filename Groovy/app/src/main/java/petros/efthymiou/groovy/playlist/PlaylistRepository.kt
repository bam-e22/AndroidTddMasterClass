package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow

class PlaylistRepository {
    suspend fun getPlayLists(): Flow<Result<List<Playlist>>> {
        TODO("Not yet implemented")
    }
}