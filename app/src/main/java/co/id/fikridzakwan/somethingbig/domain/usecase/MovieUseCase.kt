package co.id.fikridzakwan.somethingbig.domain.usecase

import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import io.reactivex.Single

interface MovieUseCase {

    fun getTrendingMovies(): Single<List<Movie>>

    fun getNowPlayingMovies(): Single<List<Movie>>

    fun getUpcomingMovies(): Single<List<Movie>>

    fun getDetailMovie(id: Int): Single<Detail>
}