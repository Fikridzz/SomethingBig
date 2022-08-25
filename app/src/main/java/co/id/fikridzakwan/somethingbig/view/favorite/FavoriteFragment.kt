package co.id.fikridzakwan.somethingbig.view.favorite

import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.common.customview.gone
import co.id.fikridzakwan.common.customview.visible
import co.id.fikridzakwan.common.utils.BaseFragment
import co.id.fikridzakwan.common.utils.Resource
import co.id.fikridzakwan.presentation.viewmodel.FavoriteViewModel
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.databinding.FragmentFavoriteBinding
import co.id.fikridzakwan.somethingbig.view.detail.DetailMovieActivity
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val viewModel: FavoriteViewModel by viewModel()

    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(
            onItemClick = {
                DetailMovieActivity.start(requireContext(), it)
            }
        )
    }

    override fun getViewBinding(): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater)

    override fun initUI() {
        with(binding.rvFavoriteMovie) {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun initProcess() {
        viewModel.getFavoriteMovies()
    }

    override fun initAction() {
        favoriteAdapter.onFavoriteClick = { movieId ->
            viewModel.deleteFavoriteMovie(movieId)
        }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.getFavoriteMovies.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.rvFavoriteMovie.gone()
                    }
                    is Resource.Success -> {
                        if (!it.data.isNullOrEmpty()) {
                            binding.rvFavoriteMovie.visible()
                            favoriteAdapter.submitList(it.data)
                        } else {
                            binding.rvFavoriteMovie.gone()
                            binding.groupError.visible()
                        }
                    }
                    is Resource.Error -> {
                        binding.tvPlaceholder.text = "Something wrong"
                        binding.imgPlaceholder.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.error_404_image
                            )
                        )
                        binding.groupError.visible()
                    }
                }
            }
        }
    }
}