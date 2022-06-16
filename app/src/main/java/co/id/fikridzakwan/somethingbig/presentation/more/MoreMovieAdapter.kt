package co.id.fikridzakwan.somethingbig.presentation.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.loadImage
import co.id.fikridzakwan.somethingbig.databinding.ItemMoreMovieBinding
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import com.bumptech.glide.Glide

class MoreMovieAdapter(private val onItemClickListener: (Movie) -> Unit) : RecyclerView.Adapter<MoreMovieAdapter.MoreViewHolder>() {

    private val listItem = ArrayList<Movie>()

    fun setData(listData: List<Movie>?) {
        if (listData == null) return
        listItem.clear()
        listItem.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreViewHolder =
        MoreViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_more_movie, parent, false))

    override fun onBindViewHolder(holder: MoreViewHolder, position: Int) {
        val data = listItem[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    inner class MoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMoreMovieBinding.bind(itemView)
        fun bind(data: Movie) {
            binding.apply {
                imgPoster.loadImage(data.posterPath ?: "", itemView.context)
                tvTitle.text = data.title
                tvDescription.text = data.overview
                tvDate.text = data.releaseDate
                tvGenres.text = data.genres
            }
            itemView.setOnClickListener {
                onItemClickListener(data)
            }
        }
    }
}