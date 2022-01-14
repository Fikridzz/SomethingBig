package co.id.fikridzakwan.somethingbig.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import co.id.fikridzakwan.somethingbig.utils.DataMapper.mapToDetail
import co.id.fikridzakwan.somethingbig.utils.DataMapper.mapToMovie
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

class MovieInteractor @Inject constructor(private val repository: IMovieRepository) : MovieUseCase {

    override fun getTrendingMovies(): Single<List<Movie>> {
        return repository.getTrendingMovies()
            .map {
                it.map { result ->
                    result.mapToMovie()
                }
            }
    }

    override fun getNowPlayingMovies(): Single<List<Movie>> {
        return repository.getNowPlayingMovies()
            .map { it.map { it.mapToMovie() } }
    }

    override fun getUpcomingMovies(): Single<List<Movie>> {
        return repository.getUpcomingMovies()
            .map { it.map { it.mapToMovie() } }
    }

    override fun getDetailMovie(id: Int): Single<Detail> {
        return repository.getDetailMovie(id)
            .map { it.mapToDetail() }
    }

    override fun getMoreMovie(value: String): Flowable<PagingData<UiModel>> {
        return repository.getMoreMovie(value)
            .map {
                it.map { UiModel.MovieItem(it.mapToMovie()) }
            }
    }

    override fun searchMovies(query: String): Single<List<Movie>> {
        return repository.searchMovies(query)
            .map { it.map { it.mapToMovie() } }
    }

    sealed class UiModel {
        data class MovieItem(val movie: Movie) : UiModel()
    }
}
