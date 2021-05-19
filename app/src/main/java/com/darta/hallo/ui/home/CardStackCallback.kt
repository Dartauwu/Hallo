package com.darta.hallo.ui.home

import androidx.recyclerview.widget.DiffUtil
import java.util.ArrayList

class CardStackCallback(
    private val old: List<ItemModel>,
    private val baru: ArrayList<Any?>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return baru.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return old[oldItemPosition] === baru[newItemPosition]
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return old[oldItemPosition] === baru[newItemPosition]
    }

}