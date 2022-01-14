package co.id.fikridzakwan.somethingbig.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieInteractor
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieUseCase
import co.id.fikridzakwan.somethingbig.utils.BaseViewModel
import co.id.fikridzakwan.somethingbig.utils.RxUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : BaseViewModel() {

    val getResult = MutableLiveData<Resource<PagingData<MovieInteractor.UiModel>>>()

    fun searchMovie(query: String) {
        getResult.value = Resource.Loading()

        disposable.add(
            movieUseCase.searchMovies(query)
                .compose(RxUtils.applyFlowableAsync())
                .subscribe({ value ->
                    getResult.value = Resource.Success(value)
                }, this::onError)
        )
    }

    override fun onError(error: Throwable) {
        getResult.value = Resource.Error(error.localizedMessage ?: "")
    }
}