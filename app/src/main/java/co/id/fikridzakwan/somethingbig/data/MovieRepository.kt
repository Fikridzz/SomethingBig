package co.id.fikridzakwan.somethingbig.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.data.source.model.MovieEntity
import co.id.fikridzakwan.somethingbig.data.source.network.MovieApi
import co.id.fikridzakwan.somethingbig.data.source.paging.MoreMoviePagingSource
import co.id.fikridzakwan.somethingbig.data.source.paging.SearchMoviePagingSource
import co.id.fikridzakwan.somethingbig.data.source.response.DetailResponse
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import co.id.fikridzakwan.somethingbig.data.source.room.MovieDao
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import co.id.fikridzakwan.somethingbig.utils.ReqStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository(private val movieApi: MovieApi, private val movieDb: MovieDao) : IMovieRepository {
    override suspend fun getTrendingMovies(): Flow<List<ResultsItem>> {
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

    override suspend fun getNowPlayingMovies(): Flow<List<ResultsItem>> {
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

    override suspend fun getUpcomingMovies(): Flow<List<ResultsItem>> {
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

    override suspend fun getDetailMovie(id: Int): Flow<DetailResponse> {
        return flow {
            val response = movieApi.getDetailMovie(id, BuildConfig.API_KEY)
            val data = response.body()
            if (data != null) {
                emit(data)
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMoreMovie(value: String): Flow<PagingData<ResultsItem>> {
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

    override suspend fun searchMovies(query: String): Flow<PagingData<ResultsItem>> {
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

    override suspend fun getFavoriteMovies(): Flow<List<MovieEntity>> {
        return withContext(Dispatchers.IO) {
            movieDb.getFavoriteMovies()
        }
    }

    override suspend fun getFavoriteMovieById(movieId: Int) : Flow<MovieEntity> {
        return withContext(Dispatchers.IO) {
            movieDb.getFavoriteMovieById(movieId)
        }
    }

    override suspend fun insertFavoriteMovie(data: MovieEntity) {
        return withContext(Dispatchers.IO) {
            movieDb.insertFavoriteMovie(data)
        }
    }

    override suspend fun deleteFavoriteMovie(movieId: Int) {
        return withContext(Dispatchers.IO) {
            movieDb.deleteFavoriteMovie(movieId)
        }
    }

    override suspend fun deleteAllFavoriteMovie() {
        return withContext(Dispatchers.IO) {
            movieDb.deleteAllFavoriteMovies()
        }
    }
}