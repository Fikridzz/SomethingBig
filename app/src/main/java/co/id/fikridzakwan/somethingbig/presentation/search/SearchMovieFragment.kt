package co.id.fikridzakwan.somethingbig.presentation.search

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.FragmentSearchMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.presentation.paging.MoviePagerAdapter
import co.id.fikridzakwan.somethingbig.presentation.paging.ReposLoadStateAdapter
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchMovieBinding>() {

    private val moviePager: MoviePagerAdapter by lazy {
        MoviePagerAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id)
            }
        )
    }

    private val viewModel: SearchMovieViewModel by viewModels()

    override fun getViewBinding() = FragmentSearchMovieBinding.inflate(layoutInflater)

    override fun initUI() {
        binding.apply {
            toolbar.setTitle("Search")
            binding.progressLinear.gone()

            with(rvSearch) {
                layoutManager = LinearLayoutManager(context)
                adapter = moviePager
                    .withLoadStateHeaderAndFooter(
                        header = ReposLoadStateAdapter(context = context, retry = { moviePager.retry() }),
                        footer = ReposLoadStateAdapter(context = context, retry = { moviePager.retry() })
                    )
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
                    is Resource.Loading -> { binding.progressLinear.visible() }
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
}