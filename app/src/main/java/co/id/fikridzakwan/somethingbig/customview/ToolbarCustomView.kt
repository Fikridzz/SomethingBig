package co.id.fikridzakwan.somethingbig.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import co.id.fikridzakwan.somethingbig.R

class ToolbarCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val btnBack: AppCompatImageButton
    private val tvTitle: AppCompatTextView

    init {
        inflate(context, R.layout.layout_toolbar, this)
        btnBack = findViewById(R.id.btn_back)
        tvTitle = findViewById(R.id.tv_title_toolbar)
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun backPressed() {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}