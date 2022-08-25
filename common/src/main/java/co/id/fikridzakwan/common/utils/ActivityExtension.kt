package co.id.fikridzakwan.common.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import co.id.fikridzakwan.common.R

fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        statusBarColor = Color.TRANSPARENT
    }
}

fun Activity.resetStatusBarColor() {
    window.apply {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        statusBarColor = ContextCompat.getColor(this@resetStatusBarColor, R.color.color_status_bar)
    }
}