package com.fakhry.loonly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fakhry.loonly.R
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.const.Const
import com.fakhry.loonly.databinding.ItemCaraouselBinding
import com.fakhry.loonly.helper.Settings

class CarouselAdapter : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {
    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarouselAdapter.CarouselViewHolder =
        CarouselViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_caraousel, parent, false)
        )

    override fun onBindViewHolder(holder: CarouselAdapter.CarouselViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCaraouselBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(Const.BASE_IMAGE + data.backdropPath)
                    .into(ivBackdrop)
                tvTitle.text = data.title
                itemView.setOnClickListener {
                    onItemClick?.invoke(listData[layoutPosition])
                }

                val prefs = PreferenceManager.getDefaultSharedPreferences(itemView.context)
                when (prefs.getInt(Settings.THEMES_MODE, Settings.DEFAULT_THEMES_MODE)) {
                    AppCompatDelegate.MODE_NIGHT_YES ->
                        binding.imgBg.setImageResource(R.drawable.shape_rec_bg_black_gradient)
                    else ->
                        binding.imgBg.setImageResource(R.drawable.shape_rec_bg_white_gradient)
                }
            }
        }
    }
}