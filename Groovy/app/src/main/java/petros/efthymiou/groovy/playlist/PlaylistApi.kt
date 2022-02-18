package petros.efthymiou.groovy.playlist

interface PlaylistApi {
    suspend fun fetchAllPlayLists(): List<Playlist> {
        return listOf()
    }
}