package co.id.fikridzakwan.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.id.fikridzakwan.data.BuildConfig
import co.id.fikridzakwan.data.source.network.MovieApiClient
import co.id.fikridzakwan.data.source.response.ResultsItem
import retrofit2.HttpException
import java.io.IOException

class SearchMoviePagingSource(
    private val service: MovieApiClient,
    private val query: String
) : PagingSource<Int, ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val position = params.key ?: 1
        return try {
            val response = service.searchMovies(BuildConfig.API_KEY, query, position)
            val repos = response.body()?.results
            LoadResult.Page(
                data = repos.orEmpty(),
                prevKey = if (position == 1) null else position - 1,
                nextKey = when {
                    position == response.body()?.totalPages -> null
                    response.body()?.results.isNullOrEmpty() -> null
                    else -> position + 1
                }
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}