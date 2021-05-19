package com.darta.hallo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.darta.hallo.R
import com.squareup.picasso.Picasso

class CardStackAdapter(var items: List<ItemModel>) :
    RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.setData(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var nama: TextView
        var usia: TextView
        var kota: TextView
        fun setData(data: ItemModel) {
            Picasso.get()
                .load(data.image)
                .fit()
                .centerCrop()
                .into(image)
            nama.text = data.nama
            usia.text = data.usia
            kota.text = data.kota
        }

        init {
            image = itemView.findViewById(R.id.item_image)
            nama = itemView.findViewById(R.id.item_name)
            usia = itemView.findViewById(R.id.item_age)
            kota = itemView.findViewById(R.id.item_city)
        }
    }

}