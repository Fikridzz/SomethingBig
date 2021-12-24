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
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.FragmentMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import android.graphics.ColorMatrixColorFilter

import android.graphics.ColorMatrix
import android.transition.TransitionInflater
import android.util.Log
import androidx.navigation.fragment.findNavController
import co.id.fikridzakwan.somethingbig.R
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
    }

    override fun initProcess() {
        viewModel.getPopularMovies()
        viewModel.getNowPlayingMovies()
        viewModel.getUpcomingMovies()
    }

    override fun initAction() {
        binding.apply {
            // Set animation on fragment when click header movie
            headerNowPlaying.setOnClickListener { findNavController().navigate(R.id.action_nav_movie_to_nav_more_movie) }
            headerUpcoming.setOnClickListener { findNavController().navigate(R.id.action_nav_movie_to_nav_more_movie) }
        }
    }

    override fun initObservers() {
        viewModel.getTrending.observe(viewLifecycleOwner, {
            when(it) {
                is Resource.Loading -> {
                    binding.shimmerLayout1.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.shimmerLayout1.visibility = View.GONE
                    trendingMovieAdapter.setData(it.data)
//                        listType.add(1)
//                        movieAdapter.setData(it.data)

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
                    binding.shimmerLayout1.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        with(binding.rvPopular) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingMovieAdapter
        }

        viewModel.getNowPlaying.observe(viewLifecycleOwner, {
            when(it) {
                is Resource.Loading -> {
                    binding.shimmerLayout2.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.shimmerLayout2.visibility = View.GONE
                    nowPlayingMovieAdapter.setData(it.data)
//                        listType.add(2)
//                        movieAdapter.setData(it.data)
                }
                is Resource.Error -> {
                    binding.shimmerLayout2.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        with(binding.rvNowPlaying) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = nowPlayingMovieAdapter
        }

        viewModel.getUpcoming.observe(viewLifecycleOwner, {
            when(it) {
                is Resource.Loading -> {
                    binding.shimmerLayout3.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.shimmerLayout3.visibility = View.GONE
                    upcomingMovieAdapter.setData(it.data)
//                        listType.add(3)
//                        movieAdapter.setData(it.data)
                }
                is Resource.Error -> {
                    binding.shimmerLayout3.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        with(binding.rvUpcoming) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingMovieAdapter
        }
    }
}