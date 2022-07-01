package co.id.fikridzakwan.somethingbig.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.fikridzakwan.core.data.Resource
import co.id.fikridzakwan.core.domain.model.Movie
import co.id.fikridzakwan.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val getTrending = MutableStateFlow<Resource<MutableList<Movie>>>(Resource.Loading())
    val getNowPlaying = MutableStateFlow<Resource<MutableList<Movie>>>(
        Resource.Loading()
    )
    val getUpcoming = MutableStateFlow<Resource<MutableList<Movie>>>(Resource.Loading())

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