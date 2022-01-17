package co.id.fikridzakwan.somethingbig.presentation.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieUseCase
import co.id.fikridzakwan.somethingbig.utils.BaseViewModel
import co.id.fikridzakwan.somethingbig.utils.RxUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    val getTrending = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val getNowPlaying = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val getUpcoming = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())

    fun getTrendingMovie() {
        viewModelScope.launch {
            getTrending.value = Resource.Loading()

            movieUseCase.getTrendingMovies()
                .catch { getTrending.value = Resource.Error(it.localizedMessage ?: "") }
                .collect {
                    getTrending.value = Resource.Success(it)
                }
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            getNowPlaying.value = Resource.Loading()

            movieUseCase.getNowPlayingMovies()
                .catch { getNowPlaying.value = Resource.Error(it.localizedMessage ?: "") }
                .collect {
                    getNowPlaying.value = Resource.Success(it)
                }
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            getUpcoming.value = Resource.Loading()

            movieUseCase.getUpcomingMovies()
                .catch { getUpcoming.value = Resource.Error(it.localizedMessage ?: "") }
                .collect {
                    getUpcoming.value = Resource.Success(it)
                }
        }
    }
}