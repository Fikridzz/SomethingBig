package co.id.fikridzakwan.somethingbig.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.fikridzakwan.somethingbig.data.Resource
import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.domain.usecase.MovieUseCase
import co.id.fikridzakwan.somethingbig.utils.BaseViewModel
import co.id.fikridzakwan.somethingbig.utils.RxUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val getDetail = MutableStateFlow<Resource<Detail>>(Resource.Loading())

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
}