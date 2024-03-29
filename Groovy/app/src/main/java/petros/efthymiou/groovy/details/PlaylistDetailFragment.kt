package petros.efthymiou.groovy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.databinding.FragmentPlaylistDetailBinding
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {
    lateinit var viewModel: PlaylistDetailViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailViewModelFactory
    private val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        val id = args.playlistId

        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistDetailViewModel::class.java)
        viewModel.getPlaylistDetail(id)

        viewModel.playlistDetail.observe(viewLifecycleOwner) { playlistDetail ->
            if (playlistDetail.getOrNull() != null) {
                binding.playlistName.text = playlistDetail.getOrNull()!!.name
                binding.playlistDetails.text = playlistDetail.getOrNull()!!.details
            } else {
                Snackbar.make(binding.playlistsDetailRoot, R.string.generic_error, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.loader.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> binding.detailsLoader.visibility = View.VISIBLE
                false -> binding.detailsLoader.visibility = View.GONE
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistDetailFragment()
    }
}