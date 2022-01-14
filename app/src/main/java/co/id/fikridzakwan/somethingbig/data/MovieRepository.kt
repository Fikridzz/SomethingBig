package co.id.fikridzakwan.somethingbig.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.data.source.network.MovieApi
import co.id.fikridzakwan.somethingbig.data.source.paging.MoreMoviePagingSource
import co.id.fikridzakwan.somethingbig.data.source.paging.SearchMoviePagingSource
import co.id.fikridzakwan.somethingbig.data.source.response.DetailResponse
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import androidx.paging.rxjava2.flowable as flowable

@ExperimentalCoroutinesApi
class MovieRepository @Inject constructor(private val movieApi: MovieApi) : IMovieRepository {

    override fun getTrendingMovies(): Single<List<ResultsItem>> {
        return movieApi.getTrendingMovies(BuildConfig.API_KEY, 1)
            .map {
                it.body()?.results
            }
    }

    override fun getNowPlayingMovies(): Single<List<ResultsItem>> {
        return movieApi.getNowPlayingMovies(BuildConfig.API_KEY, 1)
            .map {
                it.body()?.results
            }
    }

    override fun getUpcomingMovies(): Single<List<ResultsItem>> {
        return movieApi.getUpcomingMovies(BuildConfig.API_KEY, 1)
            .map { it.body()?.results }
    }

    override fun getDetailMovie(id: Int): Single<DetailResponse> {
        return movieApi.getDetailMovie(id, BuildConfig.API_KEY)
            .map { it.body() }
    }

    override fun getMoreMovie(value: String): Flowable<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40),
            pagingSourceFactory = { MoreMoviePagingSource(movieApi, value) }
        ).flowable
    }

    override fun searchMovies(query: String): Flowable<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40),
            pagingSourceFactory = { SearchMoviePagingSource(movieApi, query) }
        ).flowable
    }
}