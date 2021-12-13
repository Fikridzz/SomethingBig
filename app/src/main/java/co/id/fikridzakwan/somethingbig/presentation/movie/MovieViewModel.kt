package co.id.fikridzakwan.somethingbig.presentation.movie

import androidx.lifecycle.MutableLiveData
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieUseCase
import co.id.fikridzakwan.somethingbig.utils.BaseViewModel
import co.id.fikridzakwan.somethingbig.utils.RxUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : BaseViewModel() {

    val getPopulars = MutableLiveData<Resource<List<Movie>>>()

    fun getPopularMovies() {
        getPopulars.value = Resource.Loading()

        disposable.add(
            movieUseCase.getPopularMovies()
                .compose(RxUtils.applySingleAsync())
                .subscribe({ value ->
                    getPopulars.value = Resource.Success(value)
                }, this::onError)
        )
    }

    override fun onError(error: Throwable) {
        getPopulars.value = Resource.Error(error.localizedMessage!!)
    }
}