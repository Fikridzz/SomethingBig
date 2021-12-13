package co.id.fikridzakwan.somethingbig.domain.usecase

import co.id.fikridzakwan.somethingbig.domain.model.Movie
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import co.id.fikridzakwan.somethingbig.utils.DataMapper.mapToMovie
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

class MovieInteractor @Inject constructor(private val repository: IMovieRepository): MovieUseCase {

    override fun getPopularMovies(): Single<List<Movie>> {
        return repository.getPopularMovies()
            .map {
                it.map { result ->
                    result.mapToMovie()
                }
            }
    }
}
