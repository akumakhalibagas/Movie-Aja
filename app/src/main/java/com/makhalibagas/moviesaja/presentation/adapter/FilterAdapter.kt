package com.makhalibagas.moviesaja.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makhalibagas.moviesaja.databinding.ItemFilterBinding
import com.makhalibagas.moviesaja.domain.model.GenresItem

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    private val listData = ArrayList<GenresItem>()

    var onClick: ((GenresItem) -> Unit)? = null

    fun setData(newListData: List<GenresItem>) {
        val previousContentSize = listData.size
        listData.clear()
        listData.addAll(newListData)
        notifyItemRangeRemoved(0, previousContentSize)
        notifyItemRangeInserted(0, newListData.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size

    inner class ViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: GenresItem) {
            initView(data)
            initListener(data)
        }

        private fun initView(data: GenresItem) {
            binding.apply {
                rbGenre.text = data.name
            }
        }

        private fun initListener(data: GenresItem) {
            binding.rbGenre.setOnClickListener {
                onClick?.invoke(data)
            }
        }
    }
}