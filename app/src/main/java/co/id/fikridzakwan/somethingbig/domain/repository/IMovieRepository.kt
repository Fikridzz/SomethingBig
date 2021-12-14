package co.id.fikridzakwan.somethingbig.domain.repository

import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import io.reactivex.Single

interface IMovieRepository {

    fun getTrendingMovies(): Single<List<ResultsItem>>

    fun getNowPlayingMovies(): Single<List<ResultsItem>>

    fun getUpcomingMovies(): Single<List<ResultsItem>>
}