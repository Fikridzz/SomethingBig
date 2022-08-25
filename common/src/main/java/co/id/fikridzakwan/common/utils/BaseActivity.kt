package co.id.fikridzakwan.common.utils

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initViewBinding()
    }

    protected lateinit var binding: VB

    protected abstract fun getViewBinding(): VB

    protected abstract fun initUI()

    protected abstract fun initAction()

    protected abstract fun initObservers()

    protected abstract fun initProcess()

    private fun initViewBinding() {
        initUI()
        initAction()
        initObservers()
        initProcess()
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}