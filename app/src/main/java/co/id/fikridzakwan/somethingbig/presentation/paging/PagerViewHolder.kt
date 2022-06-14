package co.id.fikridzakwan.somethingbig.presentation.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.loadImage
import co.id.fikridzakwan.somethingbig.databinding.ItemMoreMovieBinding
import co.id.fikridzakwan.somethingbig.domain.model.Movie

class PagerViewHolder(private val binding: ItemMoreMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Movie, onItemClickListener: (Movie) -> Unit) {
            with(binding) {
                imgPoster.loadImage(data.posterPath, itemView.context)
                tvTitle.text = data.title
                tvDescription.text = data.overview
                tvDate.text = data.releaseDate
                tvGenres.text = data.genres
        }
        itemView.setOnClickListener {
            onItemClickListener(data)
        }
    }

    companion object {
        fun create(parent: ViewGroup): PagerViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_more_movie, parent, false)
            val binding = ItemMoreMovieBinding.bind(view)

            return PagerViewHolder(binding)
        }
    }
}