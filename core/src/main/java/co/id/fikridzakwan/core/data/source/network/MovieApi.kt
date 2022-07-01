package co.id.fikridzakwan.core.data.source.network

import javax.inject.Inject

class MovieApi @Inject constructor(private val apiClient: MovieApiClient) : MovieApiClient {
    override suspend fun getTrendingMovies(apiKey: String, page: Int) = apiClient.getTrendingMovies(apiKey, page)

    override suspend fun getNowPlayingMovies(apiKey: String, page: Int) = apiClient.getNowPlayingMovies(apiKey, page)

    override suspend fun getUpcomingMovies(apiKey: String, page: Int) = apiClient.getUpcomingMovies(apiKey, page)

    override suspend fun getMoreMovie(
        value: String,
        apiKey: String,
        page: Int
    ) = apiClient.getMoreMovie(value, apiKey, page)

    override suspend fun getDetailMovie(id: Int, apiKey: String) = apiClient.getDetailMovie(id, apiKey)

    override suspend fun searchMovies(
        apiKey: String,
        query: String,
        page: Int
    ) = apiClient.searchMovies(apiKey, query, page)
}