package com.darta.hallo.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darta.hallo.R

import com.makeramen.roundedimageview.RoundedImageView

class PostAdapter(postItems: List<PostItem>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private val postItems: List<PostItem>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.post_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.setPostImage(postItems[position])
    }

    override fun getItemCount(): Int {
        return postItems.size
    }

    class PostViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var postImageView: RoundedImageView
        fun setPostImage(postItem: PostItem) {
            postImageView.setImageResource(postItem.image)
        }

        init {
            postImageView = itemView.findViewById(R.id.imagePost)
        }
    }

    init {
        this.postItems = postItems
    }
}