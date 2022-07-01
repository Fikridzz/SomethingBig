package co.id.fikridzakwan.somethingbig.presentation.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.fikridzakwan.core.data.Resource
import co.id.fikridzakwan.core.domain.model.Detail
import co.id.fikridzakwan.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val getFavoriteMovies = MutableStateFlow<Resource<List<Detail>>>(Resource.Loading())

    fun getFavoriteMovies() {
        viewModelScope.launch {
            getFavoriteMovies.value = Resource.Loading()
            movieUseCase.getFavoriteMovies()
                .catch { getFavoriteMovies.value = Resource.Error(it.localizedMessage ?: "") }
                .collect {
                    getFavoriteMovies.value = Resource.Success(it)
                }
        }
    }

    fun deleteFavoriteMovie(movieId: Int) {
        viewModelScope.launch {
            try {
                movieUseCase.deleteFavoriteMovie(movieId)
            } catch (e: Exception) {
                Log.d("Ewowr database" , e.localizedMessage.toString())
            }
        }
    }
}