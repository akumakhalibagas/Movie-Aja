package com.makhalibagas.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.makhalibagas.core.R
import com.makhalibagas.core.databinding.ItemMovieBinding
import com.makhalibagas.core.domain.model.Movie

class MovieAdapter :
    PagingDataAdapter<Movie, MovieAdapter.ViewHolder>(
        ItemMovieDiffCallback()
    ) {

    var onDetailClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) holder.bind(data)
    }

    inner class ViewHolder(
        private val binding: ItemMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val item = getItem(layoutPosition)
                if (item != null) {
                    onDetailClick?.invoke(item)
                }
            }
        }

        fun bind(data: Movie) {
            binding.apply {
                imgMovie.load(data.posterPath) {
                    placeholder(R.color.black)
                }
                tvTitle.text = data.originalTitle
                tvDesc.text = data.overview
                tvVote.text = data.voteAverage.toString()
            }
        }
    }
}

class ItemMovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie,
    ): Boolean {
        return oldItem.id == newItem.id && oldItem.originalTitle == newItem.originalTitle
    }
}