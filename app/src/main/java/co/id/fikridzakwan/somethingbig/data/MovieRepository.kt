package co.id.fikridzakwan.somethingbig.data

import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.data.source.network.MovieApi
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import co.id.fikridzakwan.somethingbig.domain.repository.IMovieRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

class MovieRepository @Inject constructor(private val movieApi: MovieApi) : IMovieRepository {

    override fun getPopularMovies(): Single<List<ResultsItem>> {
        return movieApi.getPopularMovies(BuildConfig.API_KEY, 1)
            .map {
                it.body()?.results
            }
    }
}