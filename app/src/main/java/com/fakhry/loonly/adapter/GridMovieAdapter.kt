package com.fakhry.loonly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fakhry.loonly.R
import com.fakhry.loonly.core.const.Const
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.databinding.ItemMovBackdropMiniBinding
import com.fakhry.loonly.helper.Settings
import java.util.*

class GridMovieAdapter : RecyclerView.Adapter<GridMovieAdapter.GridViewHolder>() {

    private var _binding: ItemMovBackdropMiniBinding? = null
    private val binding get() = _binding!!

    val listData = ArrayList<Movie>()
    var onItemClick: ((Int) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        _binding =
            ItemMovBackdropMiniBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding.root)
    }


    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(Const.BASE_IMAGE + data.backdropPath)
                    .into(imgBackdrop)
                tvTitle.text = data.title

                val prefs = PreferenceManager.getDefaultSharedPreferences(itemView.context)
                when (prefs.getInt(Settings.THEMES_MODE, Settings.DEFAULT_THEMES_MODE)) {
                    AppCompatDelegate.MODE_NIGHT_YES ->
                        binding.imgBg.setImageResource(R.drawable.shape_rec_bg_black_gradient)
                    else ->
                        binding.imgBg.setImageResource(R.drawable.shape_rec_bg_white_gradient)
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[layoutPosition].id)
            }
        }
    }
}