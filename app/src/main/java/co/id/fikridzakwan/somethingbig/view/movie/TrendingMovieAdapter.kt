package co.id.fikridzakwan.somethingbig.view.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.common.customview.gone
import co.id.fikridzakwan.common.customview.loadImage
import co.id.fikridzakwan.common.customview.visible
import co.id.fikridzakwan.domain.model.Movie
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieLargeBinding

class TrendingMovieAdapter(
    private val onItemClickListener: (Movie) -> Unit,
    private val onItemMoreClickListener: () -> Unit
) : ListAdapter<Movie, TrendingMovieAdapter.PopularViewHolder>(PopularMovieCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view = ItemMovieLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<Movie>?) {
        super.submitList(list?.map { it.copy() })
    }

    override fun getItemCount(): Int {
        val limit = 11
        return if (currentList.size > limit) limit else currentList.size
    }

    inner class PopularViewHolder(private val binding: ItemMovieLargeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            with(binding) {
                imgPosterLarge.loadImage(data.posterPath ?: "", itemView.context)
                imgPosterLarge.clipToOutline = true
                if (data.id == 1) {
                    imgPosterLarge.gone()
                    groupMore.visible()
                } else {
                    imgPosterLarge.visible()
                    groupMore.gone()
                }
            }
            itemView.setOnClickListener {
                if (data.id == 1) onItemMoreClickListener() else onItemClickListener(data)
            }
        }
    }

    companion object {
        val PopularMovieCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}