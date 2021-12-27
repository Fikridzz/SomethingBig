package co.id.fikridzakwan.somethingbig.presentation.more

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.FragmentMoreMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import co.id.fikridzakwan.somethingbig.presentation.main.MainActivity
import co.id.fikridzakwan.somethingbig.presentation.movie.MovieFragment
import co.id.fikridzakwan.somethingbig.utils.AppConstants
import co.id.fikridzakwan.somethingbig.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreMovieFragment : BaseFragment<FragmentMoreMovieBinding>() {

    private val viewModel: MoreMovieViewModel by viewModels()

    private val moreMovieAdapter: MoreMovieAdapter by lazy {
        MoreMovieAdapter(
            onItemClickListener = {
                DetailMovieActivity.start(requireContext(), it.id)
            }
        )
    }

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
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_left)
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        binding.toolbar.setTitle("More movie")
        binding.toolbar.backPressed()

        with(binding.rvMoreMovie) {
            layoutManager = LinearLayoutManager(context)
            adapter = moreMovieAdapter
        }
    }

    override fun initProcess() {
        if (arguments != null) {
            val value = arguments?.getString(AppConstants.EXTRA_TYPE)
            value?.let { viewModel.getMoreMovie(it) }
        }
    }

    override fun initAction() {
    }

    override fun initObservers() {
        viewModel.getMoreMovie.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> binding.progressBar.visible()
                is Resource.Success -> {
                    binding.progressBar.gone()
                    moreMovieAdapter.setData(it.data)
                }
                is Resource.Error -> {
                    binding.progressBar.gone()
                    showToast(it.message.toString())
                }
            }
        })
    }
}