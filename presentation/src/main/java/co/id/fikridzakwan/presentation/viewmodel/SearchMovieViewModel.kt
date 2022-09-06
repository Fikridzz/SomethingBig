package co.id.fikridzakwan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.id.fikridzakwan.common.utils.Resource
import co.id.fikridzakwan.domain.usecase.MovieInteractor
import co.id.fikridzakwan.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val getResult = MutableStateFlow<Resource<PagingData<MovieInteractor.UiModel>>>(
        Resource.Loading()
    )

    fun searchMovie(query: String?) {

        viewModelScope.launch {
            getResult.value = Resource.Loading()

            movieUseCase.searchMovies(query ?: "")
                .cachedIn(viewModelScope)
                .catch {
                    getResult.value =
                        Resource.Error(it.localizedMessage ?: "")
                }
                .collect { getResult.value = Resource.Success(it) }
        }
    }
}