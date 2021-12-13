package co.id.fikridzakwan.somethingbig.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieLargeBinding
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieSmallBinding
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import com.bumptech.glide.Glide

class LargeMovieAdapter(
    private val onItemClickListener: (Movie) -> Unit
) : RecyclerView.Adapter<LargeMovieAdapter.LargeViewHolder>() {

    private var listItem = ArrayList<Movie>()

    fun setData(movieListData: List<Movie>?) {
        if (movieListData == null)return
        listItem.clear()
        listItem.addAll(movieListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LargeViewHolder {
        val view = ItemMovieLargeBinding.inflate(LayoutInflater.from(parent.context))
        return LargeViewHolder(view)
    }

    override fun onBindViewHolder(holder: LargeViewHolder, position: Int) {
        val data = listItem[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        val limit = 10
        if (listItem.size > limit) {
            return limit
        } else {
            return listItem.size
        }
    }

    inner class LargeViewHolder(private val binding: ItemMovieLargeBinding) : RecyclerView.ViewHolder(binding.root) {
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