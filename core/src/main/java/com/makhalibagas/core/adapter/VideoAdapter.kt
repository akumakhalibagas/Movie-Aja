package com.makhalibagas.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.makhalibagas.core.R
import com.makhalibagas.core.databinding.ItemVideoBinding
import com.makhalibagas.core.domain.model.MovieVideo

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    private val listData = ArrayList<MovieVideo>()

    var onClick: ((MovieVideo) -> Unit)? = null

    fun setData(newListData: List<MovieVideo>) {
        val previousContentSize = listData.size
        listData.clear()
        listData.addAll(newListData)
        notifyItemRangeRemoved(0, previousContentSize)
        notifyItemRangeInserted(0, newListData.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size

    inner class ViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MovieVideo) {
            initView(data)
            initListener(data)
        }

        private fun initView(data: MovieVideo) {
            binding.apply {
                imgMovie.load("https://img.youtube.com/vi/${data.key}/0.jpg") {
                    placeholder(R.color.black)
                }
            }
        }

        private fun initListener(data: MovieVideo) {
            binding.imgPlay.setOnClickListener { onClick?.invoke(data) }
        }
    }
}