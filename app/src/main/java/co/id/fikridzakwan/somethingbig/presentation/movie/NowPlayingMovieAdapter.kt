package co.id.fikridzakwan.somethingbig.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.core.domain.model.Movie
import co.id.fikridzakwan.somethingbig.customview.gone
import co.id.fikridzakwan.somethingbig.customview.loadImage
import co.id.fikridzakwan.somethingbig.customview.visible
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieSmallBinding

class NowPlayingMovieAdapter(
    private val onItemClickListener: (Movie) -> Unit,
    private val onItemMoreClickListener: () -> Unit
) : ListAdapter<Movie, NowPlayingMovieAdapter.ViewHolder>(NowPlayingMovieCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMovieSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<Movie>?) {
        super.submitList(list?.map { it.copy() })
    }

    override fun getItemCount(): Int {
        val limit = 11
        return if (currentList.size > limit) limit else currentList.size
    }

    inner class ViewHolder(private val binding: ItemMovieSmallBinding) :
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
        val NowPlayingMovieCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}