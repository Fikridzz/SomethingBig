package co.id.fikridzakwan.somethingbig.presentation.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import android.graphics.ColorMatrixColorFilter
import android.graphics.ColorMatrix
import android.transition.TransitionInflater
import android.transition.Visibility
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.databinding.FragmentMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.main.MainActivity
import co.id.fikridzakwan.somethingbig.presentation.more.MoreMovieFragment
import co.id.fikridzakwan.somethingbig.utils.AppConstants
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>() {

    private val viewModel: MovieViewModel by viewModels()

    private val trendingMovieAdapter: TrendingMovieAdapter by lazy {
        TrendingMovieAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id)
            }
        )
    }

    private val nowPlayingMovieAdapter: NowPlayingMovieAdapter by lazy {
        NowPlayingMovieAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id)
            }
        )
    }

    private val upcomingMovieAdapter: UpcomingMovieAdapter by lazy {
        UpcomingMovieAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id)
            }
        )
    }

    override fun getViewBinding() = FragmentMovieBinding.inflate(layoutInflater)

    override fun initUI() {
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_left)
        enterTransition = inflater.inflateTransition(R.transition.slide_right)

        with(binding.rvPopular) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingMovieAdapter
        }

        with(binding.rvNowPlaying) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = nowPlayingMovieAdapter
        }

        with(binding.rvUpcoming) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingMovieAdapter
        }
    }

    override fun initProcess() {
        viewModel.getTrendingMovie()
        viewModel.getNowPlayingMovies()
        viewModel.getUpcomingMovies()
    }

    override fun initAction() {
        binding.apply {

            headerNowPlaying.setOnClickListener {
                // Parsing string from fragment to another fragment
                val bundle = bundleOf(AppConstants.EXTRA_TYPE to "now_playing")
                findNavController().navigate(R.id.action_nav_movie_to_nav_more_movie, bundle)
            }
            headerUpcoming.setOnClickListener {
                val bundle = bundleOf(AppConstants.EXTRA_TYPE to "upcoming")
                findNavController().navigate(R.id.action_nav_movie_to_nav_more_movie, bundle)
            }
            headerSearch.setOnClickListener { findNavController().navigate(R.id.action_nav_movie_to_nav_search_movie) }
        }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.getTrending.collect {
                when (it) {
                    is Resource.Loading -> { binding.shimmerLayout1.visible() }
                    is Resource.Success -> {
                        binding.shimmerLayout1.gone()
                        trendingMovieAdapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        binding.shimmerLayout1.gone()
                        showToast(it.message ?: "")
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getNowPlaying.collect {
                when (it) {
                    is Resource.Loading -> { binding.shimmerLayout2.visible() }
                    is Resource.Success -> {
                        binding.shimmerLayout2.gone()
                        nowPlayingMovieAdapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        binding.shimmerLayout2.gone()
                        showToast(it.message ?: "")
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getUpcoming.collect {
                when (it) {
                    is Resource.Loading -> { binding.shimmerLayout3.visible() }
                    is Resource.Success -> {
                        binding.shimmerLayout3.gone()
                        upcomingMovieAdapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        binding.shimmerLayout3.gone()
                        showToast(it.message ?: "")
                    }
                }
            }
        }
    }
}