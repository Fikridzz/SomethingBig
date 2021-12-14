package co.id.fikridzakwan.somethingbig.domain.usecase

import co.id.fikridzakwan.somethingbig.domain.model.Movie
import io.reactivex.Single

interface MovieUseCase {

    fun getPopularMovies(): Single<List<Movie>>

    fun getNowPlayingMovies(): Single<List<Movie>>

    fun getUpcomingMovies(): Single<List<Movie>>
}