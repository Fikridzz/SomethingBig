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
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieSmallBinding

class UpcomingMovieAdapter(
    private val onItemClickListener: (Movie) -> Unit,
    private val onItemMoreClickListener: () -> Unit
) : ListAdapter<Movie, UpcomingMovieAdapter.UpcomingViewHolder>(UpcomingMovieCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val view = ItemMovieSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<Movie>?) {
        super.submitList(list?.map { it.copy() })
    }

    override fun getItemCount(): Int {
        val limit = 11
        return if (currentList.size > limit) limit else currentList.size
    }

    inner class UpcomingViewHolder(private val binding: ItemMovieSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            binding.apply {
                imgPoster.loadImage(data.posterPath ?: "", itemView.context)
                imgPoster.clipToOutline = true
                if (data.id == 1) {
                    imgPoster.gone()
                    groupMore.visible()
                } else {
                    imgPoster.visible()
                    groupMore.gone()
                }
            }
            itemView.setOnClickListener {
                if (data.id == 1) onItemMoreClickListener() else onItemClickListener(data)
            }
        }
    }

    companion object {
        val UpcomingMovieCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}