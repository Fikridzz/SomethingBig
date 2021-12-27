package co.id.fikridzakwan.somethingbig.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.databinding.ActivityMainBinding
import co.id.fikridzakwan.somethingbig.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initUI() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_movie -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    override fun initProcess() {
    }

    override fun initAction() {
    }

    override fun initObservers() {
    }

    private fun showBottomNav() {
        binding.navView.visible()
    }

    private fun hideBottomNav() {
        binding.navView.gone()
    }
}