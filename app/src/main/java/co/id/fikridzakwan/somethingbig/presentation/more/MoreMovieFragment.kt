package co.id.fikridzakwan.somethingbig.presentation.more

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.FragmentMoreMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.presentation.paging.MoviePagerAdapter
import co.id.fikridzakwan.somethingbig.presentation.paging.ReposLoadStateAdapter
import co.id.fikridzakwan.somethingbig.utils.AppConstants
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
import co.id.fikridzakwan.somethingbig.utils.resetStatusBarColor
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
            "now_playing" -> titleToolbar =  "Now playing"
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
            moviePager.addLoadStateListener { loadState ->
                // Show empty list
                val isListEmpty = loadState.refresh is LoadState.NotLoading && moviePager.itemCount == 0
                binding.groupError.isVisible = isListEmpty
                binding.rvMoreMovie.isVisible = !isListEmpty
                // Only show the list if refresh succeeds
                binding.rvMoreMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show retry state if initial load or refresh fails
                binding.groupError.isVisible = loadState.source.refresh is LoadState.Error

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    showToast(it.error.localizedMessage ?: "Error")
                }
            }
        }
    }

    override fun initProcess() {
        viewModel.getMoreMovie(type)
    }

    override fun initAction() {
        binding.btnRetry.setOnClickListener { moviePager.retry() }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.getMoreMovie.collect {
                when (it) {
                    is Resource.Loading -> { binding.progressBar.visible() }
                    is Resource.Success -> {
                        binding.progressBar.gone()
                        lifecycleScope.launch {
                            it.data?.let { v -> moviePager.submitData(v) }
                        }
                    }
                    is Resource.Error -> {
                        binding.progressBar.gone()
                        showToast(it.message ?: "")
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.resetStatusBarColor()
    }
}