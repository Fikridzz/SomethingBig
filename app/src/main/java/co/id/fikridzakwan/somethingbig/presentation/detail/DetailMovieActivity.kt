package co.id.fikridzakwan.somethingbig.presentation.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.LoadingCustomView
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.loadImage
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.ActivityDetailMovieBinding
import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.utils.AppConstants.EXTRA_ID
import co.id.fikridzakwan.somethingbig.utils.BaseActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class DetailMovieActivity : BaseActivity<ActivityDetailMovieBinding>() {

    private val viewModel: DetailMovieViewModel by viewModels()

    companion object {
        fun start(context: Context, id: Int) {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            context.startActivity(intent)
        }
    }

    override fun getViewBinding() = ActivityDetailMovieBinding.inflate(layoutInflater)

    override fun initUI() {
    }

    override fun initAction() {
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun initProcess() {
        val extra = intent.getIntExtra(EXTRA_ID, 0)
        viewModel.getDetailMovie(extra)
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.getDetail.collect {
                when (it) {
                    is Resource.Loading -> { binding.progressBar.visible() }
                    is Resource.Success -> {
                        binding.progressBar.gone()
                        it.data?.let { v -> populateDetail(v) }
                    }
                    is Resource.Error -> {
                        binding.progressBar.gone()
                        showToast(it.message ?: "")
                    }
                }
            }
        }
    }

    private fun populateDetail(data: Detail) {
        binding.apply {
            imgBackdrop.loadImage(data.posterPath, this@DetailMovieActivity)
            imgPoster.loadImage(data.posterPath, this@DetailMovieActivity)
            imgPoster.clipToOutline = true
            tvTitle.text = data.title
            tvGenres.text = data.genres
            tvDate.text = data.releaseDate
            tvRuntime.text = data.runtime
            tvOverview.text = data.overview
        }
    }
}