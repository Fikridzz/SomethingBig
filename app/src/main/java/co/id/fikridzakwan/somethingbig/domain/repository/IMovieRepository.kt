package co.id.fikridzakwan.somethingbig.domain.repository

import androidx.paging.PagingData
import co.id.fikridzakwan.somethingbig.data.source.response.DetailResponse
import co.id.fikridzakwan.somethingbig.data.source.response.MovieResponse
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import io.reactivex.Flowable
import io.reactivex.Single

interface IMovieRepository {

    fun getTrendingMovies(): Single<List<ResultsItem>>

    fun getNowPlayingMovies(): Single<List<ResultsItem>>

    fun getUpcomingMovies(): Single<List<ResultsItem>>

    fun getDetailMovie(id: Int): Single<DetailResponse>

    fun getMoreMovie(value: String) : Flowable<PagingData<ResultsItem>>

    fun searchMovies(query: String) : Single<List<ResultsItem>>
}