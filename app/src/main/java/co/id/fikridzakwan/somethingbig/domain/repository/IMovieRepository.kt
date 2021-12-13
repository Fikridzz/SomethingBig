package co.id.fikridzakwan.somethingbig.domain.repository

import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import io.reactivex.Single

interface IMovieRepository {

    fun getPopularMovies(): Single<List<ResultsItem>>
}