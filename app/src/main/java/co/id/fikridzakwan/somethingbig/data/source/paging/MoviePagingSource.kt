package co.id.fikridzakwan.somethingbig.data.source.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.data.source.network.MovieApiClient
import co.id.fikridzakwan.somethingbig.data.source.response.MovieResponse
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviePagingSource(
    private val service: MovieApiClient,
    private val type: String
) : RxPagingSource<Int, ResultsItem>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ResultsItem>> {
        val position = params.key ?: 1
        val typeRequest = type

        return service.getMoreMovie(typeRequest, BuildConfig.API_KEY, position)
            .subscribeOn(Schedulers.io())
            .map { it.body() }
            .map { toLoadResult(it, position) }
    }

    private fun toLoadResult(data: MovieResponse, position: Int) : LoadResult<Int, ResultsItem> {
        return LoadResult.Page(
            data = data.results!!,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.totalPages) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}