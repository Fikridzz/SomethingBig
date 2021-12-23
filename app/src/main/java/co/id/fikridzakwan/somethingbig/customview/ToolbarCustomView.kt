package co.id.fikridzakwan.somethingbig.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import co.id.fikridzakwan.somethingbig.R

class ToolbarCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val btnBack: AppCompatButton
    private val tvTitle: AppCompatTextView

    init {
        inflate(context, R.layout.layout_toolbar, this)
        btnBack = findViewById(R.id.btn_back)
        tvTitle = findViewById(R.id.tv_title)
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }
}