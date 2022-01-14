package co.id.fikridzakwan.somethingbig.presentation.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieInteractor
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieUseCase
import co.id.fikridzakwan.somethingbig.utils.BaseViewModel
import co.id.fikridzakwan.somethingbig.utils.RxUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : BaseViewModel() {

    val getMoreMovie = MutableLiveData<Resource<PagingData<MovieInteractor.UiModel>>>()

    fun getMoreMovie(value: String) {
        getMoreMovie.value = Resource.Loading()

        disposable.add(
            movieUseCase.getMoreMovie(value)
                .compose(RxUtils.applyFlowableAsync())
//                .cachedIn(scope = viewModelScope)
                .subscribe({ result ->
                    getMoreMovie.value = Resource.Success(result)
                }, this::onError)
        )
    }

    override fun onError(error: Throwable) {
        getMoreMovie.value = Resource.Error(error.localizedMessage!!)
    }
}