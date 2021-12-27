package co.id.fikridzakwan.somethingbig.data.source.network

import co.id.fikridzakwan.somethingbig.data.source.response.DetailResponse
import co.id.fikridzakwan.somethingbig.data.source.response.MovieResponse
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class MovieApi @Inject constructor(private val apiClient: MovieApiClient) : MovieApiClient {
    override fun getTrendingMovies(apiKey: String, page: Int)= apiClient.getTrendingMovies(apiKey, page)
    override fun getNowPlayingMovies(apiKey: String, page: Int) = apiClient.getNowPlayingMovies(apiKey, page)
    override fun getUpcomingMovies(apiKey: String, page: Int) = apiClient.getUpcomingMovies(apiKey, page)
    override fun getMoreMovie(value: String, apiKey: String, page: Int) = apiClient.getMoreMovie(value, apiKey, page)
    override fun getDetailMovie(id: Int, apiKey: String) = apiClient.getDetailMovie(id, apiKey)
    override fun searchMovies(apiKey: String, query: String, page: Int) = apiClient.searchMovies(apiKey, query, page)
}