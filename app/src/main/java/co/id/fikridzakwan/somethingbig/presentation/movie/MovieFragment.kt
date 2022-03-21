package co.id.fikridzakwan.somethingbig.presentation.movie

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.FragmentMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.utils.AppConstants
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
import co.id.fikridzakwan.somethingbig.utils.makeStatusBarTransparent
import co.id.fikridzakwan.somethingbig.utils.resetStatusBarColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment<FragmentMovieBinding>() {

    private val viewModel: MovieViewModel by viewModel()

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
        activity?.makeStatusBarTransparent()
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_left)
        enterTransition = inflater.inflateTransition(R.transition.slide_right)

        with(binding.rvTrending) {
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
                    is Resource.Loading -> {
                        binding.shimmerLayout1.visible()
                    }
                    is Resource.Success -> {
                        binding.rvTrending.visible()
                        binding.shimmerLayout1.gone()
                        trendingMovieAdapter.submitList(it.data)

                        // Random pick image backdrop from api
                        val backdrop = it.data?.asSequence()?.shuffled()?.find { true }
                        Glide.with(requireContext())
                            .load(backdrop?.backdropPath)
                            .transition(DrawableTransitionOptions.withCrossFade(1000))
                            .into(binding.imgBackdrop)

                        // Make image view black and white
                        val matrix = ColorMatrix()
                        matrix.setSaturation(0f)

                        val filter = ColorMatrixColorFilter(matrix)
                        binding.imgBackdrop.colorFilter = filter
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
                        binding.rvNowPlaying.visible()
                        binding.shimmerLayout2.gone()
                        nowPlayingMovieAdapter.submitList(it.data)
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
                        binding.rvUpcoming.visible()
                        binding.shimmerLayout3.gone()
                        upcomingMovieAdapter.submitList(it.data)
                    }
                    is Resource.Error -> {
                        binding.shimmerLayout3.gone()
                        showToast(it.message ?: "")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.resetStatusBarColor()
    }
}