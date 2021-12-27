package co.id.fikridzakwan.somethingbig.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.FragmentSearchMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.presentation.main.MainActivity
import co.id.fikridzakwan.somethingbig.presentation.more.MoreMovieAdapter
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
import com.jakewharton.rxbinding2.widget.RxSearchView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchMovieBinding>() {

    private val moreMovieAdapter: MoreMovieAdapter by lazy {
        MoreMovieAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id)
            }
        )
    }

    private val viewModel: SearchMovieViewModel by viewModels()

    override fun getViewBinding() = FragmentSearchMovieBinding.inflate(layoutInflater)

    override fun initUI() {
        binding.apply {
            with(rvSearch) {
                adapter = moreMovieAdapter
                layoutManager = LinearLayoutManager(context)
            }

            toolbar.setTitle("Search")
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
        viewModel.getResult.observe(viewLifecycleOwner, { value ->
            if (value != null) {
                when (value) {
                    is Resource.Loading -> binding.progressLinear.visible()
                    is Resource.Success -> {
                        binding.progressLinear.gone()
                        moreMovieAdapter.setData(value.data)
                    }
                    is Resource.Error -> {
                        binding.progressLinear.gone()
                        showToast(value.message.toString())
                    }
                }
            }
        })
    }
}