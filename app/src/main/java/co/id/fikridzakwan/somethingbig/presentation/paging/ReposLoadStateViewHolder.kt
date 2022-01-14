package co.id.fikridzakwan.somethingbig.presentation.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.databinding.LoadStateFooterBinding

class ReposLoadStateViewHolder(
    private val binding: LoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRefresh.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState, context: Context) {
        if (loadState is LoadState.Error) {
            Toast.makeText(context, "Data tidak dapat dimuat", Toast.LENGTH_SHORT).show()
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.btnRefresh.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit) : ReposLoadStateViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.load_state_footer, parent, false)
            val binding = LoadStateFooterBinding.bind(view)
            return ReposLoadStateViewHolder(binding, retry)
        }
    }
}