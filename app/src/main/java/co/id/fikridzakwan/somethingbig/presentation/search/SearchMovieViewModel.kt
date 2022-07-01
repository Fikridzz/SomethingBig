package co.id.fikridzakwan.somethingbig.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.id.fikridzakwan.core.domain.usecase.MovieInteractor
import co.id.fikridzakwan.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val getResult =
        MutableStateFlow<co.id.fikridzakwan.core.data.Resource<PagingData<MovieInteractor.UiModel>>>(
            co.id.fikridzakwan.core.data.Resource.Loading()
        )

    fun searchMovie(query: String) {

        viewModelScope.launch {
            getResult.value = co.id.fikridzakwan.core.data.Resource.Loading()

            movieUseCase.searchMovies(query)
                .cachedIn(viewModelScope)
                .catch {
                    getResult.value =
                        co.id.fikridzakwan.core.data.Resource.Error(it.localizedMessage ?: "")
                }
                .collect { getResult.value = co.id.fikridzakwan.core.data.Resource.Success(it) }
        }
    }
}