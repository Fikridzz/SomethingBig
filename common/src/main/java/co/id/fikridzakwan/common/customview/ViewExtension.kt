package co.id.fikridzakwan.common.customview

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import co.id.fikridzakwan.common.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun AppCompatImageView.loadImage(url: String, context: Context) {

    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    val options = RequestOptions().error(R.drawable.ic_broken_image)
    Glide
        .with(context)
        .load(url)
        .apply(options)
        .transition(DrawableTransitionOptions.withCrossFade(500))
            .placeholder(circularProgressDrawable)
            .into(this)
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}