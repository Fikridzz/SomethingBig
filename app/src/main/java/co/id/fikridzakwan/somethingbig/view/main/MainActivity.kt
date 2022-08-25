package co.id.fikridzakwan.somethingbig.view.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import co.id.fikridzakwan.common.customview.gone
import co.id.fikridzakwan.common.customview.visible
import co.id.fikridzakwan.common.utils.BaseActivity
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initUI() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_movie -> showBottomNav()
                R.id.nav_favorite -> showBottomNav()
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