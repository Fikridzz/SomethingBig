package co.id.fikridzakwan.somethingbig.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieSmallBinding
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import com.bumptech.glide.Glide

class UpcomingMovieAdapter(private val onItemClickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<UpcomingMovieAdapter.UpcomingViewHolder>() {

    val listItem = ArrayList<Movie>()

    fun setData(movieListData: List<Movie>?) {
        if (movieListData == null) return
        listItem.addAll(movieListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val view = ItemMovieSmallBinding.inflate(LayoutInflater.from(parent.context))
        return UpcomingViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val data = listItem[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        val limit = 10
        return if (listItem.size > limit) limit else listItem.size
    }

    inner class UpcomingViewHolder(private val binding: ItemMovieSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(itemView.context).load(movie.posterPath)
                .into(binding.imgPoster)
            binding.imgPoster.clipToOutline = true
            itemView.setOnClickListener {
                onItemClickListener(movie)
            }
        }
    }
}