package co.id.fikridzakwan.somethingbig.data.source.network

import co.id.fikridzakwan.somethingbig.data.source.response.MovieResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiClient {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<Response<MovieResponse>>
}