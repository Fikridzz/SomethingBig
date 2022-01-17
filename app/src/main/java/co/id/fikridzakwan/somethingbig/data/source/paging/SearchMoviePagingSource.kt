package co.id.fikridzakwan.somethingbig.data.source.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.data.source.network.MovieApiClient
import co.id.fikridzakwan.somethingbig.data.source.response.MovieResponse
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

/**
 * There is two different way to use paging in rx that i found
 * 1: Map LoadResult in loadSingle Reference Android Developer
 * 2: Using function as LoadResult Reference Medium
 */

class SearchMoviePagingSource(
    private val service: MovieApiClient,
    private val query: String
) : RxPagingSource<Int, ResultsItem>() {

    // Version 1: Using loadSingle to LoadResult
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ResultsItem>> {
        val position = params.key ?: 1
        val queryRequest = query

        return service.searchMovies(BuildConfig.API_KEY, queryRequest, position)
            .subscribeOn(Schedulers.io())
            .map { it.body() }
            .map<LoadResult<Int, ResultsItem>> { result ->
                LoadResult.Page(
                    data = result.results!!,
                    prevKey = if (position == 1) null else position -1,
                    nextKey = if (position == result.totalPages) null else position +1
                )
            }
            .onErrorReturn { e ->
                when (e) {
                    // Retrofit calls that return the body type throw either IOException for
                    is IOException -> LoadResult.Error(e)
                    // network failures, or HttpException for any non-2xx HTTP status codes.
                    is HttpException -> LoadResult.Error(e)
                    // This code reports all errors to the UI, but you can inspect/wrap the
                    // exceptions to provide more context.
                    else -> throw e
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}