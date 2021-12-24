package co.id.fikridzakwan.somethingbig.data.source.network

import co.id.fikridzakwan.somethingbig.data.source.response.DetailResponse
import co.id.fikridzakwan.somethingbig.data.source.response.MovieResponse
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class MovieApi @Inject constructor(private val apiClient: MovieApiClient) : MovieApiClient {
    override fun getTrendingMovies(apiKey: String, page: Int): Single<Response<MovieResponse>> = apiClient.getTrendingMovies(apiKey, page)
    override fun getNowPlayingMovies(apiKey: String, page: Int): Single<Response<MovieResponse>> = apiClient.getNowPlayingMovies(apiKey, page)
    override fun getUpcomingMovies(apiKey: String, page: Int): Single<Response<MovieResponse>> = apiClient.getUpcomingMovies(apiKey, page)
    override fun getDetailMovie(id: Int, apiKey: String): Single<Response<DetailResponse>> = apiClient.getDetailMovie(id, apiKey)
    override fun searchMovies(apiKey: String, query: String, page: Int): Single<Response<MovieResponse>> = apiClient.searchMovies(apiKey, query, page)
}