package co.id.fikridzakwan.somethingbig.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieInteractor
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieUseCase
import co.id.fikridzakwan.somethingbig.utils.BaseViewModel
import co.id.fikridzakwan.somethingbig.utils.RxUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    val getResult = MutableStateFlow<Resource<PagingData<MovieInteractor.UiModel>>>(Resource.Loading())

    fun searchMovie(query: String) {

        viewModelScope.launch {
            getResult.value = Resource.Loading()

            movieUseCase.searchMovies(query)
                .catch { getResult.value = Resource.Error(it.localizedMessage ?: "") }
                .collect { getResult.value = Resource.Success(it) }
        }
    }
}