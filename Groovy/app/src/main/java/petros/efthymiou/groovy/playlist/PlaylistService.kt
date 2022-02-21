package petros.efthymiou.groovy.playlist

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistService @Inject constructor(
    private val api: PlaylistApi
) {
    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistRaw>>> {
        //Log.d("COURSE", "fetchPlaylists")
        return flow {
            val result = api.fetchAllPlayLists()
            //Log.d("COURSE", "api result= $result")
            emit(Result.success(result))
        }.catch {
            //Log.d("COURSE", "error occurred $it")
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}