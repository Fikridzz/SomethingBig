package co.id.fikridzakwan.somethingbig.presentation.more

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
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class MoreMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

//    val getMoreMovie = MutableLiveData<Resource<PagingData<MovieInteractor.UiModel>>>()
    val getMoreMovie = MutableStateFlow<Resource<PagingData<MovieInteractor.UiModel>>>(Resource.Loading())

    fun getMoreMovie(value: String) {
        viewModelScope.launch {
            getMoreMovie.value = Resource.Loading()
            movieUseCase.getMoreMovie(value)
                .catch { getMoreMovie.value = Resource.Error(it.localizedMessage ?: "") }
                .collect {
                    getMoreMovie.value = Resource.Success(it)
                }
        }
    }
}