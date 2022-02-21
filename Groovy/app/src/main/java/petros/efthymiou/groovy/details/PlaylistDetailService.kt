package petros.efthymiou.groovy.details

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailService @Inject constructor(
    private val api: PlaylistDetailApi
) {
    suspend fun fetchPlaylistDetail(id: String): Flow<Result<PlaylistDetail>> {
        //Log.d("COURSE", "fetchPlaylistDetail")
        return flow {
            val result = api.fetchPlaylistDetail(id)
            //Log.d("COURSE", "result= $result")
            emit(Result.success(result))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}