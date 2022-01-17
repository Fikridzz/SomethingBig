package co.id.fikridzakwan.somethingbig.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.data.source.network.MovieApi
import co.id.fikridzakwan.somethingbig.data.source.paging.MoreMoviePagingSource
import co.id.fikridzakwan.somethingbig.data.source.paging.SearchMoviePagingSource
import co.id.fikridzakwan.somethingbig.data.source.response.DetailResponse
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import co.id.fikridzakwan.somethingbig.utils.ReqStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi: MovieApi) : IMovieRepository {
    override fun getTrendingMovies(): Flow<List<ResultsItem>> {
        return flow {
            val response = movieApi.getTrendingMovies(BuildConfig.API_KEY, 1)
            val listData = response.body()?.results
            if (!listData.isNullOrEmpty()) {
                emit(listData)
            } else {
                emit(arrayListOf())
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getNowPlayingMovies(): Flow<List<ResultsItem>> {
        return flow {
                val response = movieApi.getNowPlayingMovies(BuildConfig.API_KEY, 1)
                val listData = response.body()?.results
                if (!listData.isNullOrEmpty()) {
                    emit(listData)
                } else {
                    emit(arrayListOf())
                }
        }.flowOn(Dispatchers.IO)
    }

    override fun getUpcomingMovies(): Flow<List<ResultsItem>> {
        return flow {
                val response = movieApi.getUpcomingMovies(BuildConfig.API_KEY, 1)
                val listData = response.body()?.results
                if (!listData.isNullOrEmpty()) {
                    emit(listData)
                } else {
                    emit(arrayListOf())
                }
        }.flowOn(Dispatchers.IO)
    }

    override fun getDetailMovie(id: Int): Flow<DetailResponse> {
        return flow {
            val response = movieApi.getDetailMovie(id, BuildConfig.API_KEY)
            val data = response.body()
            if (data != null) {
                emit(data)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMoreMovie(value: String): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { MoreMoviePagingSource(movieApi, value) }
        ).flow
    }

    override fun searchMovies(query: String): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { SearchMoviePagingSource(movieApi, query) }
        ).flow
    }
}