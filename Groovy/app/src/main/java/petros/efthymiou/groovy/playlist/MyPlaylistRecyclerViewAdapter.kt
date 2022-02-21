package petros.efthymiou.groovy.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import petros.efthymiou.groovy.databinding.PlaylistItemBinding

class MyPlaylistRecyclerViewAdapter(
    private var values: List<Playlist>? = null,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PlaylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        values?.get(position)?.let { item ->
            holder.bind(item, listener)
        }
    }

    override fun getItemCount(): Int = values?.size ?: 0

    fun reloadItems(items: List<Playlist>?) {
        values = items
    }

    inner class ViewHolder(private val binding: PlaylistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist, listener: (String) -> Unit) {
            binding.playlistName.text = playlist.name
            binding.playlistCategory.text = playlist.category
            binding.playlistImage.setImageDrawable(ContextCompat.getDrawable(binding.root.context, playlist.image))
            binding.playlistItemRoot.setOnClickListener {
                listener(playlist.id)
            }
        }
    }
}