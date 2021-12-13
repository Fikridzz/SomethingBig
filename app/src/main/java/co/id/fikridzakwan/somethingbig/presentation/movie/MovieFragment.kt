package co.id.fikridzakwan.somethingbig.presentation.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.FragmentMovieBinding
import co.id.fikridzakwan.somethingbig.presentation.detail.DetailMovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()

    private val largeMovieAdapter: LargeMovieAdapter by lazy {
        LargeMovieAdapter(
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

        if (activity != null) {

            viewModel.getPopulars.observe(viewLifecycleOwner, {
                when(it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        largeMovieAdapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
            with(binding.rvPopular) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = largeMovieAdapter
            }
        }
    }

}