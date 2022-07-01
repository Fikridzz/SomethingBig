package co.id.fikridzakwan.somethingbig.presentation.more

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.core.data.Resource
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.databinding.FragmentMoreMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.presentation.paging.MoviePagerAdapter
import co.id.fikridzakwan.somethingbig.presentation.paging.ReposLoadStateAdapter
import co.id.fikridzakwan.somethingbig.utils.AppConstants
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
import co.id.fikridzakwan.somethingbig.utils.resetStatusBarColor
import org.koin.android.viewmodel.ext.android.viewModel

class MoreMovieFragment : BaseFragment<FragmentMoreMovieBinding>() {

    private val viewModel: MoreMovieViewModel by viewModel()

    private var type: String = ""

    private val moviePager: MoviePagerAdapter by lazy {
        MoviePagerAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id ?: 0)
            }
        )
    }

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
            "trending" -> titleToolbar = "Trending"
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
                        val data = it.data
                        if (data != null) {
                            moviePager.submitData(data)
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