package petros.efthymiou.groovy.playlist

import retrofit2.http.GET

interface PlaylistApi {
    @GET("playlists")
    suspend fun fetchAllPlayLists(): List<Playlist>
}