package co.id.fikridzakwan.somethingbig.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import co.id.fikridzakwan.somethingbig.utils.DataMapper.mapToDetail
import co.id.fikridzakwan.somethingbig.utils.DataMapper.mapToMovie
import co.id.fikridzakwan.somethingbig.utils.ReqStatus
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

class MovieInteractor(private val repository: IMovieRepository) : MovieUseCase {
    override fun getTrendingMovies(): Flow<MutableList<Movie>> {
        return repository.getTrendingMovies().map { it.map { it.mapToMovie() }.toMutableList() }
    }

    override fun getNowPlayingMovies(): Flow<MutableList<Movie>> {
        return repository.getNowPlayingMovies().map { it.map { it.mapToMovie() }.toMutableList() }
    }

    override fun getUpcomingMovies(): Flow<MutableList<Movie>> {
        return repository.getUpcomingMovies().map { it.map { it.mapToMovie() }.toMutableList() }
    }

    override fun getDetailMovie(id: Int): Flow<Detail> {
        return repository.getDetailMovie(id).map { it.mapToDetail() }
    }

    override fun getMoreMovie(value: String): Flow<PagingData<UiModel>> {
        return repository.getMoreMovie(value).map { it.map { UiModel.MovieItem(it.mapToMovie()) } }
    }

    override fun searchMovies(query: String): Flow<PagingData<UiModel>> {
        return repository.searchMovies(query).map { it.map { UiModel.MovieItem(it.mapToMovie()) } }
    }

    sealed class UiModel {
        data class MovieItem(val movie: Movie) : UiModel()
    }
}
