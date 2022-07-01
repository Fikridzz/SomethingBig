package co.id.fikridzakwan.core.domain.usecase

import androidx.paging.PagingData
import co.id.fikridzakwan.core.domain.model.Detail
import co.id.fikridzakwan.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    suspend fun getTrendingMovies(): Flow<MutableList<Movie>>

    suspend fun getNowPlayingMovies(): Flow<MutableList<Movie>>

    suspend fun getUpcomingMovies(): Flow<MutableList<Movie>>

    suspend fun getDetailMovie(id: Int): Flow<Detail>

    suspend fun getMoreMovie(value: String) : Flow<PagingData<MovieInteractor.UiModel>>

    suspend fun searchMovies(query: String) : Flow<PagingData<MovieInteractor.UiModel>>

    suspend fun getFavoriteMovies() : Flow<MutableList<Detail>>

    suspend fun getFavoriteMovieById(movieId: Int) : Flow<Detail>

    suspend fun insertFavoriteMovie(data: Detail)

    suspend fun deleteFavoriteMovie(movieId: Int)

    suspend fun deleteAllFavoriteMovie()
}