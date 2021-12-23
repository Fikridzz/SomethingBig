package co.id.fikridzakwan.somethingbig.presentation.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.somethingbig.databinding.ItemMoreBinding
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import com.bumptech.glide.Glide

class MoreMovieAdapter(onItemClickListener: (Movie) -> Unit) : RecyclerView.Adapter<MoreMovieAdapter.MoreViewHolder>() {

    private val listItem = ArrayList<Movie>()

    fun setData(listData: List<Movie>?) {
        if (listData == null) return
        listItem.clear()
        listItem.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreViewHolder {
        val view = ItemMoreBinding.inflate(LayoutInflater.from(parent.context))
        return MoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoreViewHolder, position: Int) {
        val data = listItem[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class MoreViewHolder(private val binding: ItemMoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            binding.apply {
                Glide.with(itemView.context).load(data.posterPath).into(imgPoster)
                tvTitle.text = data.title
                tvDescription.text = data.overview
                tvDate.text = data.releaseDate
                tvGenres.text = data.genreIds
            }
        }
    }
}