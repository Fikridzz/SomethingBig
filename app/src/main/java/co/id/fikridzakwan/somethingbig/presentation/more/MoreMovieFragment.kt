package co.id.fikridzakwan.somethingbig.presentation.more

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.FragmentMoreMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.presentation.paging.MoviePagerAdapter
import co.id.fikridzakwan.somethingbig.presentation.paging.ReposLoadStateAdapter
import co.id.fikridzakwan.somethingbig.utils.AppConstants
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreMovieFragment : BaseFragment<FragmentMoreMovieBinding>() {

    private val viewModel: MoreMovieViewModel by viewModels()

    private var type: String = ""

    private val moviePager: MoviePagerAdapter by lazy {
        MoviePagerAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id)
            }
        )
    }

//    private val moreMovieAdapter: MoreMovieAdapter by lazy {
//        MoreMovieAdapter(
//            onItemClickListener = {
//                DetailMovieActivity.start(requireContext(), it.id)
//            }
//        )
//    }

//    companion object {
//        fun start(value: String, id: Int) : MovieFragment {
//            return MovieFragment().apply {
//                val bundle = bundleOf(AppConstants.EXTRA_TYPE to  value)
//                findNavController().navigate(id, bundle)
//            }
//        }
//    }

    override fun getViewBinding() = FragmentMoreMovieBinding.inflate(layoutInflater)

    override fun initUI() {
        binding.toolbar.backPressed()
        if (arguments != null) {
            type = arguments?.getString(AppConstants.EXTRA_TYPE)!!
        }
        var titleToolbar = ""
        when (type) {
            "now_playing" -> titleToolbar = "Now playing"
            "upcoming" -> titleToolbar = "Upcoming"
        }
        binding.toolbar.setTitle(titleToolbar)

        with(binding.rvMoreMovie) {
            layoutManager = LinearLayoutManager(context)
            adapter = moviePager
                .withLoadStateHeaderAndFooter(
                    header = ReposLoadStateAdapter(context = context, retry = { moviePager.retry() }),
                    footer = ReposLoadStateAdapter(context = context, retry = { moviePager.retry() })
                )
        }
    }

    override fun initProcess() {
        viewModel.getMoreMovie(type)
    }

    override fun initAction() {
    }

    override fun initObservers() {
        viewModel.getMoreMovie.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> binding.progressBar.showLoading()
                is Resource.Success -> {
                    binding.progressBar.hideLoading()
                    viewModel.getMoreMovie.observe(viewLifecycleOwner, { result ->
                        lifecycleScope.launch {
                            result.data?.let { it1 -> moviePager.submitData(it1) }
                        }
                    })
                }
                is Resource.Error -> {
                    binding.progressBar.hideLoading()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}