package co.id.fikridzakwan.somethingbig.data.source.network

import co.id.fikridzakwan.somethingbig.data.source.response.DetailResponse
import co.id.fikridzakwan.somethingbig.data.source.response.MovieResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {

    @GET("trending/movie/week")
    fun getTrendingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<Response<MovieResponse>>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page : Int
    ): Single<Response<MovieResponse>>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<Response<MovieResponse>>

    @GET("movie/{value}")
    fun getMoreMovie(
        @Path("value") value: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<Response<MovieResponse>>

    @GET("movie/{id}")
    fun getDetailMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Single<Response<DetailResponse>>

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Single<Response<MovieResponse>>
}