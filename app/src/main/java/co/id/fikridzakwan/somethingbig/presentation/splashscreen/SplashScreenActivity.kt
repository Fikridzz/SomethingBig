package co.id.fikridzakwan.somethingbig.presentation.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.databinding.ActivitySplashScreenBinding
import co.id.fikridzakwan.somethingbig.presentation.main.MainActivity
import co.id.fikridzakwan.somethingbig.utils.makeStatusBarTransparent
import co.id.fikridzakwan.somethingbig.utils.resetStatusBarColor

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.makeStatusBarTransparent()
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.imgSplash.startAnimation(fadeIn)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.resetStatusBarColor()
    }
}