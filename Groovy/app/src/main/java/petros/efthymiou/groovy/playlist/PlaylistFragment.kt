package petros.efthymiou.groovy.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import petros.efthymiou.groovy.databinding.FragmentPlaylistBinding
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {
    private var recyclerViewAdapter: MyPlaylistRecyclerViewAdapter? = null

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory
    private lateinit var viewModel: PlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()

        val binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        recyclerViewAdapter = MyPlaylistRecyclerViewAdapter(listener = { id ->
            val action = PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(id)
            findNavController().navigate(action)
        })
        with(binding.playlistsList) {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }

        viewModel.loader.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> binding.loader.visibility = View.VISIBLE
                false -> binding.loader.visibility = View.GONE
            }
        }

        viewModel.playlists.observe(viewLifecycleOwner, { playlists ->
            recyclerViewAdapter?.reloadItems(playlists.getOrNull())
            recyclerViewAdapter?.notifyDataSetChanged()
        })

        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistFragment()
    }
}