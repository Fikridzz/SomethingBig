package co.id.fikridzakwan.somethingbig.presentation.movie

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.core.domain.model.Movie
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.databinding.FragmentMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.utils.AppConstants
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
import co.id.fikridzakwan.somethingbig.utils.makeStatusBarTransparent
import co.id.fikridzakwan.somethingbig.utils.resetStatusBarColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
                    is co.id.fikridzakwan.core.data.Resource.Loading -> {
                        binding.shimmerLayout1.visible()
                    }
                    is co.id.fikridzakwan.core.data.Resource.Success -> {
                        binding.rvTrending.visible()
                        binding.shimmerLayout1.gone()
                        it.data?.add(10, Movie(1, null, null, null, null, null, null, null, null))
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
                    is co.id.fikridzakwan.core.data.Resource.Error -> {
                        binding.shimmerLayout1.gone()
                        showToast(it.message ?: "")
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getNowPlaying.collect {
                when (it) {
                    is co.id.fikridzakwan.core.data.Resource.Loading -> {
                        binding.shimmerLayout2.visible()
                    }
                    is co.id.fikridzakwan.core.data.Resource.Success -> {
                        binding.rvNowPlaying.visible()
                        binding.shimmerLayout2.gone()
                        it.data?.add(10, Movie(1, null, null, null, null, null, null, null, null))
                        nowPlayingMovieAdapter.submitList(it.data)
                    }
                    is co.id.fikridzakwan.core.data.Resource.Error -> {
                        binding.shimmerLayout2.gone()
                        showToast(it.message ?: "")
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getUpcoming.collect {
                when (it) {
                    is co.id.fikridzakwan.core.data.Resource.Loading -> {
                        binding.shimmerLayout3.visible()
                    }
                    is co.id.fikridzakwan.core.data.Resource.Success -> {
                        binding.rvUpcoming.visible()
                        binding.shimmerLayout3.gone()
                        it.data?.add(10, Movie(1, null, null, null, null, null, null, null, null))
                        upcomingMovieAdapter.submitList(it.data)
                    }
                    is co.id.fikridzakwan.core.data.Resource.Error -> {
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