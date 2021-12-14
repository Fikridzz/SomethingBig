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

    val getTrending = MutableLiveData<Resource<List<Movie>>>()
    val getNowPlaying = MutableLiveData<Resource<List<Movie>>>()
    val getUpcoming = MutableLiveData<Resource<List<Movie>>>()

    fun getPopularMovies() {
        getTrending.value = Resource.Loading()

        disposable.add(
            movieUseCase.getTrendingMovies()
                .compose(RxUtils.applySingleAsync())
                .subscribe({ value ->
                    getTrending.value = Resource.Success(value)
                }, this::onError)
        )
    }

    fun getNowPlayingMovies() {
        getNowPlaying.value = Resource.Loading()

        disposable.add(
            movieUseCase.getNowPlayingMovies()
                .compose(RxUtils.applySingleAsync())
                .subscribe({ value ->
                    getNowPlaying.value = Resource.Success(value)
                }, this::onError)
        )
    }

    fun getUpcomingMovies() {
        getUpcoming.value = Resource.Loading()

        disposable.add(
            movieUseCase.getUpcomingMovies()
                .compose(RxUtils.applySingleAsync())
                .subscribe({ value ->
                    getUpcoming.value = Resource.Success(value)
                }, this::onError)
        )
    }

    override fun onError(error: Throwable) {
        getTrending.value = Resource.Error(error.localizedMessage!!)
        getNowPlaying.value = Resource.Error(error.localizedMessage!!)
        getUpcoming.value = Resource.Error(error.localizedMessage!!)
    }
}