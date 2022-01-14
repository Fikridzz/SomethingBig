package co.id.fikridzakwan.somethingbig.presentation.detail

import androidx.lifecycle.MutableLiveData
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieUseCase
import co.id.fikridzakwan.somethingbig.utils.BaseViewModel
import co.id.fikridzakwan.somethingbig.utils.RxUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : BaseViewModel() {

    val getDetail = MutableLiveData<Resource<Detail>>()

    fun getDetailMovie(id: Int) {
        getDetail.value = Resource.Loading()

        disposable.add(
            movieUseCase.getDetailMovie(id)
                .compose(RxUtils.applySingleAsync())
                .subscribe({ value ->
                    getDetail.value = Resource.Success(value)
                }, this::onError)
        )
    }

    override fun onError(error: Throwable) {
        getDetail.value = Resource.Error(error.localizedMessage!!)
    }
}