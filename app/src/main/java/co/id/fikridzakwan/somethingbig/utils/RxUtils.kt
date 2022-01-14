package co.id.fikridzakwan.somethingbig.utils

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxUtils {
    fun <T> applyFlowableAsync(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable ->
            flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applySingleAsync(): SingleTransformer<T, T> {
        return SingleTransformer { single ->
            single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applyObservableMainThread(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applyObservableCompute(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}