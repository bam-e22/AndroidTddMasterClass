package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.R
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistRaw>, List<Playlist>> {
    override fun invoke(playlistsRaw: List<PlaylistRaw>): List<Playlist> {
        return playlistsRaw.map {
            val image = when (it.category) {
                "rock" -> R.drawable.rock
                else -> R.drawable.playlist
            }

            Playlist(it.id, it.name, it.category, image)
        }
    }
}