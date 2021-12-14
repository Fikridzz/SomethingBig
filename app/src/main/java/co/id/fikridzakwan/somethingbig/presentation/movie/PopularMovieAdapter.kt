package co.id.fikridzakwan.somethingbig.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieLargeBinding
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import com.bumptech.glide.Glide

class PopularMovieAdapter(
    private val onItemClickListener: (Movie) -> Unit
) : RecyclerView.Adapter<PopularMovieAdapter.PopularViewHolder>() {

    private var listItem = ArrayList<Movie>()

    fun setData(movieListData: List<Movie>?) {
        if (movieListData == null)return
        listItem.clear()
        listItem.addAll(movieListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view = ItemMovieLargeBinding.inflate(LayoutInflater.from(parent.context))
        return PopularViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val data = listItem[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        val limit = 10
        return if (listItem.size > limit) limit else listItem.size
    }

    inner class PopularViewHolder(private val binding: ItemMovieLargeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                Glide.with(itemView.context).load(BuildConfig.BASE_URL_IMAGE + movie.posterPath).into(binding.imgPosterLarge)
                imgPosterLarge.clipToOutline = true
                itemView.setOnClickListener {
                    onItemClickListener(movie)
                }
            }
        }
    }
}