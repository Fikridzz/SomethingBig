package co.id.fikridzakwan.somethingbig.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.customview.loadImage
import co.id.fikridzakwan.somethingbig.databinding.ItemMovieFavoriteBinding
import co.id.fikridzakwan.somethingbig.domain.model.Detail

class FavoriteAdapter(
    private val onItemClick: (Int) -> Unit
) : ListAdapter<Detail, FavoriteAdapter.ViewHolder>(FavoriteCallback) {

    var onFavoriteClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMovieFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMovieFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Detail) {
            binding.apply {
                tvTitle.text = data.title
                tvDate.text = data.releaseDate
                tvGenres.text = data.genres
                imgPoster.loadImage(data.posterPath, itemView.context)
                btnFavorite.setOnClickListener {
                    onFavoriteClick?.invoke(data.id)
//                    cekFavoriteState(data.isFavorite)
                }
            }
            itemView.setOnClickListener {
                onItemClick(data.id)
            }
        }

//        private fun cekFavoriteState(favorite: Boolean) {
//            var favoriteState = favorite
//            favoriteState = !favoriteState
//            setFavoriteState(favoriteState)
//        }
//
//        private fun setFavoriteState(favoriteState: Boolean) {
//            if (favoriteState) {
//                binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite))
//            } else {
//                binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_border))
//            }
//        }
    }

    companion object {
        val FavoriteCallback = object : DiffUtil.ItemCallback<Detail>() {
            override fun areItemsTheSame(oldItem: Detail, newItem: Detail): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Detail, newItem: Detail): Boolean {
                return oldItem == newItem
            }
        }
    }
}