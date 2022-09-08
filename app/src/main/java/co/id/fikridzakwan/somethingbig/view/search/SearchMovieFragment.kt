package co.id.fikridzakwan.somethingbig.view.search

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.common.customview.gone
import co.id.fikridzakwan.common.customview.visible
import co.id.fikridzakwan.common.utils.BaseFragment
import co.id.fikridzakwan.common.utils.Resource
import co.id.fikridzakwan.common.utils.resetStatusBarColor
import co.id.fikridzakwan.presentation.viewmodel.SearchMovieViewModel
import co.id.fikridzakwan.somethingbig.databinding.FragmentSearchMovieBinding
import co.id.fikridzakwan.somethingbig.view.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.view.paging.MoviePagerAdapter
import co.id.fikridzakwan.somethingbig.view.paging.ReposLoadStateAdapter
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchMovieBinding>() {

    private val viewModel: SearchMovieViewModel by viewModel()
    private var querySearch = ""

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
            progressLinear.gone()
            srcMovie.requestFocus()

            with(rvSearch) {
                layoutManager = LinearLayoutManager(context)
                adapter = moviePager
                    .withLoadStateHeaderAndFooter(
                        header = ReposLoadStateAdapter(
                            context = context,
                            retry = { moviePager.retry() }),
                        footer = ReposLoadStateAdapter(
                            context = context,
                            retry = { moviePager.retry() })
                    )
                moviePager.addLoadStateListener { loadState ->

                    when (loadState.source.refresh) {
                        is LoadState.NotLoading -> {
                            binding.progressLinear.gone()
                            if (querySearch.isNotEmpty() && moviePager.itemCount == 0) binding.groupError.visible()
                            binding.rvSearch.visible()
                        }
                        is LoadState.Loading -> {
                            binding.progressLinear.visible()
                            binding.groupError.gone()
                            binding.rvSearch.gone()
                        }
                        is LoadState.Error -> {
                            binding.progressLinear.gone()
                            binding.groupError.visible()
                            binding.rvSearch.gone()
                            val errorState = loadState.source.append as? LoadState.Error
                            binding.tvEmptyList.text = errorState?.error?.message
                        }
                    }
                }
            }
        }
    }

    override fun initProcess() {
        binding.srcMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchMovie(query)
                querySearch = query
                binding.progressLinear.visible()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                querySearch = query
                return false
            }
        })
    }

    override fun initAction() {
        binding.apply {
            toolbar.btnBack.setOnClickListener { findNavController().popBackStack() }
            btnRetry.setOnClickListener { viewModel.searchMovie(querySearch) }
        }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.getResult.collect {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        binding.progressLinear.gone()
                        lifecycleScope.launch {
                            it.data?.let { v -> moviePager.submitData(v) }
                        }
                    }
                    is Resource.Error -> {
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