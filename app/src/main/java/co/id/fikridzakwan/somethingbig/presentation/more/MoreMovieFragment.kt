package co.id.fikridzakwan.somethingbig.presentation.more

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.databinding.FragmentMoreMovieBinding
import co.id.fikridzakwan.somethingbig.utils.BaseFragment

class MoreMovieFragment : BaseFragment<FragmentMoreMovieBinding>() {
    override fun getViewBinding() = FragmentMoreMovieBinding.inflate(layoutInflater)

    override fun initUI() {
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_left)
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        binding.toolbar.setTitle("More movie")
        binding.toolbar.backPressed()
    }

    override fun initProcess() {
    }

    override fun initAction() {
    }

    override fun initObservers() {
    }
}