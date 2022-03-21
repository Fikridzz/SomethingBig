package co.id.fikridzakwan.somethingbig.presentation.detail

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.loadImage
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.databinding.ActivityDetailMovieBinding
import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.utils.AppConstants.EXTRA_ID
import co.id.fikridzakwan.somethingbig.utils.BaseActivity
import co.id.fikridzakwan.somethingbig.utils.resetStatusBarColor
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : BaseActivity<ActivityDetailMovieBinding>() {

    private val viewModel: DetailMovieViewModel by viewModel()
    private var isCover = false

    companion object {
        fun start(context: Context, id: Int) {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            context.startActivity(intent)
        }
    }

    override fun getViewBinding() = ActivityDetailMovieBinding.inflate(layoutInflater)

    override fun initUI() {
        this.resetStatusBarColor()
    }

    override fun initAction() {
        binding.apply {
            detailAnimation()
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
                    is Resource.Loading -> {
                        binding.progressBar.visible()
                    }
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
            imgBackdrop.loadImage(data.backdropPath, this@DetailMovieActivity)
            imgPoster.loadImage(data.posterPath, this@DetailMovieActivity)
            imgPoster.clipToOutline = true
            tvTitle.text = data.title
            tvGenres.text = data.genres
            tvDate.text = data.releaseDate
            tvRuntime.text = data.runtime
            tvOverview.text = data.overview
        }
    }

    private fun detailAnimation() {
        val coverConstraintSet = ConstraintSet()
        coverConstraintSet.clone(this, R.layout.full_image_movie_detail)

        val initConstraintSet = ConstraintSet()
        initConstraintSet.clone(this, R.layout.activity_detail_movie)

        binding.imgPoster.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root)
            coverConstraintSet.applyTo(binding.root)

            val anim = ValueAnimator()
            anim.setIntValues(Color.BLACK, Color.WHITE)
            anim.setEvaluator(ArgbEvaluator())
            anim.addUpdateListener {
                binding.apply {
                    imageCalendar.setColorFilter(it.animatedValue as Int)
                    imageTime.setColorFilter(it.animatedValue as Int)
                    tvTitle.setTextColor(it.animatedValue as Int)
                    tvRuntime.setTextColor(it.animatedValue as Int)
                    tvDate.setTextColor(it.animatedValue as Int)
                }
            }
            anim.duration = 300
            anim.start()
            isCover = true
        }

        binding.btnBack.setOnClickListener {
            if (isCover) {
                TransitionManager.beginDelayedTransition(binding.root)
                initConstraintSet.applyTo(binding.root)

                val anim = ValueAnimator()
                anim.setIntValues(Color.WHITE, Color.BLACK)
                anim.setEvaluator(ArgbEvaluator())
                anim.addUpdateListener {
                    val configuration = Configuration()
                    val currentNightMode = this.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)

                    binding.apply {
                        when (currentNightMode) {
                            Configuration.UI_MODE_NIGHT_YES -> {
                                imageCalendar.setColorFilter(Color.WHITE)
                                imageTime.setColorFilter(Color.WHITE)
                                tvTitle.setTextColor(Color.WHITE)
                                tvRuntime.setTextColor(Color.WHITE)
                                tvDate.setTextColor(Color.WHITE)
                            }
                            Configuration.UI_MODE_NIGHT_NO -> {
                                imageCalendar.setColorFilter(Color.BLACK)
                                imageTime.setColorFilter(Color.BLACK)
                                tvTitle.setTextColor(Color.BLACK)
                                tvRuntime.setTextColor(Color.BLACK)
                                tvDate.setTextColor(Color.BLACK)
                            }
                            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                                imageCalendar.setColorFilter(Color.BLACK)
                                imageTime.setColorFilter(Color.BLACK)
                                tvTitle.setTextColor(Color.BLACK)
                                tvRuntime.setTextColor(Color.BLACK)
                                tvDate.setTextColor(Color.BLACK)
                            }
                        }
                    }
                }
                anim.duration = 300
                anim.start()
                isCover = false
            } else {
                onBackPressed()
            }
        }
    }
}