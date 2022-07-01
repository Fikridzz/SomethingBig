package co.id.fikridzakwan.somethingbig.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.core.domain.model.Movie
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieLargeBinding
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieSmallBinding
import com.bumptech.glide.Glide

class MovieAdapter(
    private val onItemClickListener: (Movie) -> Unit,
    private val viewType: List<Int>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listItem = ArrayList<Movie>()

    fun setData(listData: List<Movie>?) {
        if (listData == null) return
        listItem.clear()
        listItem.addAll(listData)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (viewType.size) {
            viewType[0] -> TYPE_1
            viewType[1] -> TYPE_2
            viewType[2] -> TYPE_3
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_1 -> {
                val view = ItemMovieLargeBinding.inflate(inflater)
                TrendingViewHolder(view)
            }
            TYPE_2 -> {
                val view = ItemMovieSmallBinding.inflate(inflater)
                NowPlayingViewHolder(view)
            }
            TYPE_3 -> {
                val view = ItemMovieSmallBinding.inflate(inflater)
                UpcomingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_1 -> {
                val viewHolder = holder as TrendingViewHolder
                viewHolder.bind(listItem[position])
            }
            TYPE_2 -> {
                val viewHolder = holder as NowPlayingViewHolder
                viewHolder.bind(listItem[position])
            }
            TYPE_3 -> {
                val viewHoler = holder as UpcomingViewHolder
                viewHoler.bind(listItem[position])
            }
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun getItemCount(): Int {
        val limit = 10
        return if (listItem.size > limit) limit else listItem.size
    }

    inner class TrendingViewHolder(private val binding: ItemMovieLargeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            binding.apply {
                Glide.with(itemView.context).load(data.posterPath).into(imgPosterLarge)
                imgPosterLarge.clipToOutline = true
            }
            itemView.setOnClickListener {
                onItemClickListener(data)
            }
        }
    }

    inner class NowPlayingViewHolder(private val binding: ItemMovieSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            binding.apply {
                Glide.with(itemView.context).load(data.posterPath).into(imgPoster)
                imgPoster.clipToOutline = true
            }
            itemView.setOnClickListener {
                onItemClickListener(data)
            }
        }
    }

    inner class UpcomingViewHolder(private val binding: ItemMovieSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            binding.apply {
                Glide.with(itemView.context).load(data.posterPath).into(imgPoster)
                imgPoster.clipToOutline = true
            }
            itemView.setOnClickListener {
                onItemClickListener(data)
            }
        }
    }

    companion object {
        const val TYPE_1 = 1
        const val TYPE_2 = 2
        const val TYPE_3 = 3
    }
}