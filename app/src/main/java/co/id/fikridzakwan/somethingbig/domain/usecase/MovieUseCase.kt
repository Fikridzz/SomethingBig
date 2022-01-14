package co.id.fikridzakwan.somethingbig.domain.usecase

import androidx.paging.PagingData
import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import io.reactivex.Flowable
import io.reactivex.Single

interface MovieUseCase {

    fun getTrendingMovies(): Single<List<Movie>>

    fun getNowPlayingMovies(): Single<List<Movie>>

    fun getUpcomingMovies(): Single<List<Movie>>

    fun getDetailMovie(id: Int): Single<Detail>

    fun getMoreMovie(value: String) : Flowable<PagingData<MovieInteractor.UiModel>>

    fun searchMovies(query: String) : Flowable<PagingData<MovieInteractor.UiModel>>
}