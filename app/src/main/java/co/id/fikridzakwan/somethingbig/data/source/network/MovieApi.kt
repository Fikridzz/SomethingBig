package co.id.fikridzakwan.somethingbig.data.source.network

import co.id.fikridzakwan.somethingbig.data.source.response.MovieResponse
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class MovieApi @Inject constructor(private val apiClient: MovieApiClient) : MovieApiClient {
    override fun getPopularMovies(apiKey: String, page: Int): Single<Response<MovieResponse>> = apiClient.getPopularMovies(apiKey, page)
}