package co.id.fikridzakwan.somethingbig.domain.usecase

import co.id.fikridzakwan.somethingbig.domain.model.Movie
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import co.id.fikridzakwan.somethingbig.utils.DataMapper.mapToMovie
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
}
