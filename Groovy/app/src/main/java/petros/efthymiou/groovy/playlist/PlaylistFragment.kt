package petros.efthymiou.groovy.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import petros.efthymiou.groovy.R

class PlaylistFragment : Fragment() {
    private var recyclerViewAdapter: MyPlaylistRecyclerViewAdapter? = null

    private lateinit var viewModelFactory: PlaylistViewModelFactory
    private lateinit var viewModel: PlaylistViewModel
    private val repository = PlaylistRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()

        val view = inflater.inflate(R.layout.fragment_playlist, container, false)
        if (view is RecyclerView) {
            recyclerViewAdapter = MyPlaylistRecyclerViewAdapter()
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = recyclerViewAdapter
            }
        }
        viewModel.playlists.observe(viewLifecycleOwner, { playlists ->
            recyclerViewAdapter?.reloadItems(playlists.getOrNull())
            recyclerViewAdapter?.notifyDataSetChanged()
        })

        return view
    }

    private fun setupViewModel() {
        viewModelFactory = PlaylistViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistFragment()
    }
}