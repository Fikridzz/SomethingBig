package co.id.fikridzakwan.somethingbig.presentation.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.data.source.room.MovieDatabase
import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

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