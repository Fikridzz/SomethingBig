package co.id.fikridzakwan.somethingbig.view.movie

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.common.customview.gone
import co.id.fikridzakwan.common.customview.loadImage
import co.id.fikridzakwan.common.customview.visible
import co.id.fikridzakwan.common.utils.*
import co.id.fikridzakwan.domain.model.Movie
import co.id.fikridzakwan.presentation.viewmodel.MovieViewModel
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.databinding.FragmentMovieBinding
import co.id.fikridzakwan.somethingbig.view.detail.DetailMovieActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment<FragmentMovieBinding>() {

    private val viewModel: MovieViewModel by viewModel()

    private val trendingMovieAdapter: TrendingMovieAdapter by lazy {
        TrendingMovieAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id ?: 0)
            },
            onItemMoreClickListener = {
                navigateToMoreMovie("trending")
            }
        )
    }

    private val nowPlayingMovieAdapter: NowPlayingMovieAdapter by lazy {
        NowPlayingMovieAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id ?: 0)
            },
            onItemMoreClickListener = {
                navigateToMoreMovie("now_playing")
            }
        )
    }

    private val upcomingMovieAdapter: UpcomingMovieAdapter by lazy {
        UpcomingMovieAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id ?: 0)
            },
            onItemMoreClickListener = {
                navigateToMoreMovie("upcoming")
            }
        )
    }

    override fun getViewBinding() = FragmentMovieBinding.inflate(layoutInflater)

    override fun initUI() {
        activity?.makeStatusBarTransparent()
//        val inflater = TransitionInflater.from(requireContext())
//        exitTransition = inflater.inflateTransition(R.transition.slide_left)
//        enterTransition = inflater.inflateTransition(R.transition.slide_right)

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
                navigateToMoreMovie("now_playing")
            }
            headerUpcoming.setOnClickListener {
                navigateToMoreMovie("upcoming")
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

                        // Random pick image backdrop from api
                        val backdrop = it.data?.asReversed()?.shuffled()?.find { true }
                        binding.imgBackdrop.loadImage(
                            backdrop?.backdropPath.toString(),
                            requireContext()
                        )

                        it.data?.add(10, Movie(1, null, null, null, null, null, null, null, null))
                        trendingMovieAdapter.submitList(it.data)

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
                    is Resource.Loading -> {
                        binding.shimmerLayout2.visible()
                    }
                    is Resource.Success -> {
                        binding.rvNowPlaying.visible()
                        binding.shimmerLayout2.gone()
                        it.data?.add(10, Movie(1, null, null, null, null, null, null, null, null))
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
                    is Resource.Loading -> {
                        binding.shimmerLayout3.visible()
                    }
                    is Resource.Success -> {
                        binding.rvUpcoming.visible()
                        binding.shimmerLayout3.gone()
                        it.data?.add(10, Movie(1, null, null, null, null, null, null, null, null))
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

    private fun navigateToMoreMovie(type: String) {
        val bundle = bundleOf(AppConstants.EXTRA_TYPE to type)
        findNavController().navigate(R.id.action_nav_movie_to_nav_more_movie, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.resetStatusBarColor()
    }
}