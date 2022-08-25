package co.id.fikridzakwan.common.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import co.id.fikridzakwan.common.R

class SomethingBigToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    var attributeSet: TypedArray
    var toolbar: ConstraintLayout
    var tvTitle: AppCompatTextView
    var btnBack: AppCompatImageButton
    var btnIcon: AppCompatImageButton

    init {
        inflate(context, R.layout.layout_toolbar, this)
        attributeSet = context.obtainStyledAttributes(attrs, R.styleable.SomethingBigToolbar)

        toolbar = findViewById(R.id.toolbar)
        tvTitle = findViewById(R.id.tv_title_toolbar)
        btnBack = findViewById(R.id.btn_back)
        btnIcon = findViewById(R.id.btn_icon)

        attributeSet.recycle()
    }

    fun setTitle(value: String) {
        tvTitle.text = value
    }

    fun backPressed() {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}