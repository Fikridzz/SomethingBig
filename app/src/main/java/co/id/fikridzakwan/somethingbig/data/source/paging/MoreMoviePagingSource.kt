package co.id.fikridzakwan.somethingbig.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.data.source.network.MovieApiClient
import co.id.fikridzakwan.somethingbig.data.source.response.MovieResponse
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class MoreMoviePagingSource(
    private val service: MovieApiClient,
    private val type: String
) : PagingSource<Int, ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val position = params.key ?: 1
        return try {
            val response = if (type == "trending") {
                service.getTrendingMovies(BuildConfig.API_KEY, position)
            } else {
                service.getMoreMovie(type, BuildConfig.API_KEY, position)
            }
            val repos = response.body()?.results
            LoadResult.Page(
                data = repos!!,
                prevKey = if (position == 1) null else position -1,
                nextKey = if (position == response.body()?.totalPages) null else position + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}