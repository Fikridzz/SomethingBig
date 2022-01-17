package co.id.fikridzakwan.somethingbig.domain.usecase

import androidx.paging.PagingData
import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import co.id.fikridzakwan.somethingbig.utils.ReqStatus
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getTrendingMovies(): Flow<List<Movie>>

    fun getNowPlayingMovies(): Flow<List<Movie>>

    fun getUpcomingMovies(): Flow<List<Movie>>

    fun getDetailMovie(id: Int): Flow<Detail>

    fun getMoreMovie(value: String) : Flow<PagingData<MovieInteractor.UiModel>>

    fun searchMovies(query: String) : Flow<PagingData<MovieInteractor.UiModel>>
}