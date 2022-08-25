package co.id.fikridzakwan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.fikridzakwan.common.utils.Resource
import co.id.fikridzakwan.domain.model.Detail
import co.id.fikridzakwan.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val getDetail = MutableStateFlow<Resource<Detail>>(Resource.Loading())
    val dataFromDb = MutableStateFlow<Resource<Detail>>(Resource.Loading())
    val insertFavorite = MutableStateFlow<Resource<Detail>>(Resource.Loading())
    val deleteFavorite = MutableStateFlow<Resource<Detail>>(Resource.Loading())

    fun getDetailMovie(id: Int) {
        viewModelScope.launch {
            getDetail.value = Resource.Loading()

            movieUseCase.getDetailMovie(id)
                .catch { getDetail.value = Resource.Error(it.localizedMessage ?: "") }
                .collect {
                    getDetail.value = Resource.Success(it)
                }
        }
    }

    fun getDetailMovieFromDb(id: Int) {
        viewModelScope.launch {
            dataFromDb.value = Resource.Loading()
            movieUseCase.getFavoriteMovieById(id)
                .catch { dataFromDb.value = Resource.Error(it.localizedMessage) }
                .collect { dataFromDb.value = Resource.Success(it) }
        }
    }

    fun insertFavoriteMovie(data: Detail) {
        viewModelScope.launch {
            movieUseCase.insertFavoriteMovie(data)
        }
    }

    fun deleteFavoriteMovie(movieId: Int) {
        viewModelScope.launch {
            movieUseCase.deleteFavoriteMovie(movieId)
        }
    }
}