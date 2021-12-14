package co.id.fikridzakwan.somethingbig.presentation.movie

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

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPopularMovies()
        viewModel.getNowPlayingMovies()
        viewModel.getUpcomingMovies()

        if (activity != null) {

            viewModel.getTrending.observe(viewLifecycleOwner, {
                when(it) {
                    is Resource.Loading -> {
                        binding.shimmerLayout1.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.shimmerLayout1.visibility = View.GONE
                        trendingMovieAdapter.setData(it.data)

                        // Random pick image backdrop from api
                        val backdrop = it.data?.asSequence()?.shuffled()?.find { true }
                        Glide.with(requireContext()).load(BuildConfig.BASE_URL_IMAGE + backdrop?.backdropPath).into(binding.imgBackdrop)

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
}