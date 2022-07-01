package co.id.fikridzakwan.somethingbig.presentation.search

import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.databinding.FragmentSearchMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.presentation.paging.MoviePagerAdapter
import co.id.fikridzakwan.somethingbig.presentation.paging.ReposLoadStateAdapter
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
import co.id.fikridzakwan.somethingbig.utils.resetStatusBarColor
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchMovieBinding>() {

    private val viewModel: SearchMovieViewModel by viewModel()

    private val moviePager: MoviePagerAdapter by lazy {
        MoviePagerAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id ?: 0)
            }
        )
    }

    override fun getViewBinding() = FragmentSearchMovieBinding.inflate(layoutInflater)

    override fun initUI() {
        binding.apply {
            toolbar.setTitle("Search")
            binding.progressLinear.gone()
            binding.srcMovie.onWindowFocusChanged(true)

            with(rvSearch) {
                layoutManager = LinearLayoutManager(context)
                adapter = moviePager
                    .withLoadStateHeaderAndFooter(
                        header = ReposLoadStateAdapter(context = context, retry = { moviePager.retry() }),
                        footer = ReposLoadStateAdapter(context = context, retry = { moviePager.retry() })
                    )
                moviePager.addLoadStateListener { loadState ->
                    val isListEmpty = loadState.refresh is LoadState.NotLoading && moviePager.itemCount == 0
                    binding.groupError.isVisible = isListEmpty
                    binding.progressLinear.isVisible = !isListEmpty
                    binding.rvSearch.isVisible = loadState.source.refresh is LoadState.NotLoading
                    binding.progressLinear.isVisible = loadState.source.refresh is LoadState.Loading
                    binding.groupError.isVisible = loadState.source.refresh is LoadState.Error
                }
            }
        }
    }

    override fun initProcess() {
        binding.srcMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchMovie(query)
                binding.progressLinear.visible()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun initAction() {
        binding.apply {
            toolbar.btnBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.getResult.collect {
                when (it) {
                    is co.id.fikridzakwan.core.data.Resource.Loading -> {}
                    is co.id.fikridzakwan.core.data.Resource.Success -> {
                        binding.progressLinear.gone()
                        lifecycleScope.launch {
                            it.data?.let { v -> moviePager.submitData(v) }
                        }
                    }
                    is co.id.fikridzakwan.core.data.Resource.Error -> {
                        binding.progressLinear.gone()
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