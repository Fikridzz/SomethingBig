package co.id.fikridzakwan.somethingbig.domain.repository

import androidx.paging.PagingData
import co.id.fikridzakwan.somethingbig.data.source.response.DetailResponse
import co.id.fikridzakwan.somethingbig.data.source.response.MovieResponse
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import co.id.fikridzakwan.somethingbig.utils.ReqStatus
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getTrendingMovies(): Flow<List<ResultsItem>>

    fun getNowPlayingMovies(): Flow<List<ResultsItem>>

    fun getUpcomingMovies(): Flow<List<ResultsItem>>

    fun getDetailMovie(id: Int): Flow<DetailResponse>

    fun getMoreMovie(value: String) : Flow<PagingData<ResultsItem>>

    fun searchMovies(query: String) : Flow<PagingData<ResultsItem>>
}