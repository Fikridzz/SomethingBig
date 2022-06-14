package co.id.fikridzakwan.somethingbig.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.FragmentFavoriteBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
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
                        binding.imgPlaceholder.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.error_404_image))
                        binding.groupError.visible()
                    }
                }
            }
        }
    }
}