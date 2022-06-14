package co.id.fikridzakwan.somethingbig.utils

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import co.id.fikridzakwan.somethingbig.R
import com.google.android.material.theme.overlay.MaterialThemeOverlay

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