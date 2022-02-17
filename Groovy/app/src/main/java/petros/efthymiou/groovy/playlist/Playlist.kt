package petros.efthymiou.groovy.playlist

import androidx.annotation.DrawableRes
import petros.efthymiou.groovy.R

data class Playlist(
    val id: String,
    val name: String,
    val category: String,
    @DrawableRes val image: Int = R.drawable.playlist
)