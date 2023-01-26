package com.makhalibagas.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.makhalibagas.core.databinding.ItemReviewBinding
import com.makhalibagas.core.domain.model.MovieReview

class ReviewAdapter :
    PagingDataAdapter<MovieReview, ReviewAdapter.ViewHolder>(
        ItemReviewDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) holder.bind(data)
    }

    inner class ViewHolder(
        private val binding: ItemReviewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MovieReview) {
            binding.apply {
                tvAuthor.text = data.author
                tvDesc.text = data.content
            }
        }
    }
}

class ItemReviewDiffCallback : DiffUtil.ItemCallback<MovieReview>() {
    override fun areItemsTheSame(
        oldItem: MovieReview,
        newItem: MovieReview,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieReview,
        newItem: MovieReview,
    ): Boolean {
        return oldItem.id == newItem.id
    }
}