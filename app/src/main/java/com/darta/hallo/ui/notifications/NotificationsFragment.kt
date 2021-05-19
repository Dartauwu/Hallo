package com.darta.hallo.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.darta.hallo.R
import java.util.ArrayList

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)


        val postRecyclerView = root.findViewById<RecyclerView>(R.id.postRecyclerView)
        postRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val postItems: MutableList<PostItem> = ArrayList<PostItem>()
        postItems.add(PostItem(R.drawable.d_img1))
        postItems.add(PostItem(R.drawable.f_img1))
        postItems.add(PostItem(R.drawable.f_img1))
        postItems.add(PostItem(R.drawable.f_img1))
        postItems.add(PostItem(R.drawable.d_img1))
        postItems.add(PostItem(R.drawable.f_img1))
        postItems.add(PostItem(R.drawable.d_img1))
        postItems.add(PostItem(R.drawable.f_img1))
        postItems.add(PostItem(R.drawable.d_img1))
        postItems.add(PostItem(R.drawable.f_img1))

        postRecyclerView.adapter = PostAdapter(postItems)
        return root
    }
}