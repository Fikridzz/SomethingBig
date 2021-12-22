package co.id.fikridzakwan.somethingbig.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import co.id.fikridzakwan.somethingbig.R
import com.google.android.material.progressindicator.CircularProgressIndicator

class LoadingCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val loading: LinearLayout

    init {
        inflate(context, R.layout.layout_loading, this)
        loading = findViewById(R.id.linear_loading)
    }

    fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loading.visibility = View.GONE
    }
}