package co.id.fikridzakwan.somethingbig.presentation.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieInteractor
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MoreMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val getMoreMovie = MutableStateFlow<Resource<PagingData<MovieInteractor.UiModel>>>(Resource.Loading())

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