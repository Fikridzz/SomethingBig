package co.id.fikridzakwan.somethingbig.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.customview.loadImage
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieSmallBinding
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import com.bumptech.glide.Glide

class UpcomingMovieAdapter(
    private val onItemClickListener: (Movie) -> Unit
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

    inner class UpcomingViewHolder(private val binding: ItemMovieSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            binding.apply {
                imgPoster.loadImage(data.posterPath, itemView.context)
                imgPoster.clipToOutline = true
            }
            itemView.setOnClickListener {
                onItemClickListener(data)
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