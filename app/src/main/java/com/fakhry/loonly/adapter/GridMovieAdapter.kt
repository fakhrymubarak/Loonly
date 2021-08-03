package com.fakhry.loonly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fakhry.loonly.core.BuildConfig
import com.fakhry.loonly.R
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.databinding.ItemMovBackdropMiniBinding
import java.util.*

class GridMovieAdapter : RecyclerView.Adapter<GridMovieAdapter.GridViewHolder>() {
    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun addData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GridViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_mov_backdrop_mini, parent, false)
        )

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovBackdropMiniBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(BuildConfig.BASE_IMAGE + data.backdropPath)
                    .into(imgBackdrop)
                tvTitle.text = data.title
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[layoutPosition])
            }
        }
    }
}