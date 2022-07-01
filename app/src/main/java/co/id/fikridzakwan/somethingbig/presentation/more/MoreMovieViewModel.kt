package co.id.fikridzakwan.somethingbig.presentation.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.id.fikridzakwan.core.data.Resource
import co.id.fikridzakwan.core.domain.usecase.MovieInteractor
import co.id.fikridzakwan.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MoreMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val getMoreMovie =
        MutableStateFlow<co.id.fikridzakwan.core.data.Resource<PagingData<MovieInteractor.UiModel>>>(
            co.id.fikridzakwan.core.data.Resource.Loading()
        )

    fun getMoreMovie(value: String) {
        viewModelScope.launch {
            getMoreMovie.value = Resource.Loading()
            movieUseCase.getMoreMovie(value)
                .cachedIn(viewModelScope)
                .catch { getMoreMovie.value = Resource.Error(it.localizedMessage ?: "") }
                .collect {
                    getMoreMovie.value = Resource.Success(it)
                }
        }
    }
}