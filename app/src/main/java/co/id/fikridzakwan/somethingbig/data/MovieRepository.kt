package co.id.fikridzakwan.somethingbig.data

import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.data.source.network.MovieApi
import co.id.fikridzakwan.somethingbig.data.source.response.DetailResponse
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

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
}