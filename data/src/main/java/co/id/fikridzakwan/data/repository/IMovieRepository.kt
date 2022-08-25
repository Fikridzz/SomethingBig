package co.id.fikridzakwan.data.repository

import androidx.paging.PagingData
import co.id.fikridzakwan.data.source.model.MovieEntity
import co.id.fikridzakwan.data.source.response.DetailResponse
import co.id.fikridzakwan.data.source.response.ResultsItem
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    suspend fun getTrendingMovies(): Flow<List<ResultsItem>>

    suspend fun getNowPlayingMovies(): Flow<List<ResultsItem>>

    suspend fun getUpcomingMovies(): Flow<List<ResultsItem>>

    suspend fun getDetailMovie(id: Int): Flow<DetailResponse>

    suspend fun getMoreMovie(value: String): Flow<PagingData<ResultsItem>>

    suspend fun searchMovies(query: String): Flow<PagingData<ResultsItem>>

    suspend fun getFavoriteMovies(): Flow<List<MovieEntity>>

    suspend fun getFavoriteMovieById(movieId: Int): Flow<MovieEntity>

    suspend fun insertFavoriteMovie(data: MovieEntity)

    suspend fun deleteFavoriteMovie(movieId: Int)

    suspend fun deleteAllFavoriteMovie()
}