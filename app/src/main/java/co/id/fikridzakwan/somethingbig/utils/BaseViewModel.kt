package co.id.fikridzakwan.somethingbig.utils

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    abstract fun onError(error: Throwable)
}