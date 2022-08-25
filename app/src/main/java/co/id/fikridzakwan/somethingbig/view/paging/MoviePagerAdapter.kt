package co.id.fikridzakwan.somethingbig.view.paging

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import co.id.fikridzakwan.domain.model.Movie
import co.id.fikridzakwan.domain.usecase.MovieInteractor

class MoviePagerAdapter(
    private val onItemClickListener: (Movie) -> Unit
) : PagingDataAdapter<MovieInteractor.UiModel, PagerViewHolder>(UIMODEL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
       return PagerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is MovieInteractor.UiModel.MovieItem -> holder.bind(uiModel.movie, onItemClickListener)
                else -> Unit
            }
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<MovieInteractor.UiModel>() {
            override fun areItemsTheSame(
                oldItem: MovieInteractor.UiModel,
                newItem: MovieInteractor.UiModel
            ): Boolean {
                return(oldItem is MovieInteractor.UiModel.MovieItem && newItem is MovieInteractor.UiModel.MovieItem &&
                        oldItem.movie.id == newItem.movie.id)
            }

            override fun areContentsTheSame(
                oldItem: MovieInteractor.UiModel,
                newItem: MovieInteractor.UiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}