package com.darta.hallo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.darta.hallo.R
import com.yuyakaido.android.cardstackview.*

class HomeFragment : Fragment() {

    private var manager: CardStackLayoutManager? = null
    private var adapter: CardStackAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root =
            inflater.inflate(R.layout.fragment_home, container, false)
        init(root)
        return root
    }

    private fun init(root: View) {
        val cardStackView: CardStackView = root.findViewById(R.id.card_stack_view)
        manager = CardStackLayoutManager(context, object : CardStackListener {
            override fun onCardDragging(
                direction: Direction,
                ratio: Float
            ) {
                Log.d(
                    TAG,
                    "onCardDragging: d=" + direction.name + " ratio=" + ratio
                )
            }

            override fun onCardSwiped(direction: Direction) {
                Log.d(
                    TAG,
                    "onCardSwiped: p=" + manager!!.topPosition + " d=" + direction
                )
                if (direction == Direction.Right)

                    if (direction == Direction.Left)


                    // Paginating
                        if (manager!!.topPosition == adapter!!.getItemCount() - 5) {
                            paginate()
                        }
            }

            override fun onCardRewound() {
                Log.d(
                    TAG,
                    "onCardRewound: " + manager!!.topPosition
                )
            }

            override fun onCardCanceled() {
                Log.d(
                    TAG,
                    "onCardRewound: " + manager!!.topPosition
                )
            }

            override fun onCardAppeared(
                view: View,
                position: Int
            ) {
                val tv = view.findViewById<TextView>(R.id.item_name)
                Log.d(
                    TAG,
                    "onCardAppeared: " + position + ", nama: " + tv.text
                )
            }

            override fun onCardDisappeared(
                view: View,
                position: Int
            ) {
                val tv = view.findViewById<TextView>(R.id.item_name)
                Log.d(
                    TAG,
                    "onCardAppeared: " + position + ", nama: " + tv.text
                )
            }
        })
        manager!!.setStackFrom(StackFrom.None)
        manager!!.setVisibleCount(3)
        manager!!.setTranslationInterval(8.0f)
        manager!!.setScaleInterval(0.95f)
        manager!!.setSwipeThreshold(0.3f)
        manager!!.setMaxDegree(20.0f)
        manager!!.setDirections(Direction.HORIZONTAL)
        manager!!.setCanScrollHorizontal(true)
        manager!!.setSwipeableMethod(SwipeableMethod.Manual)
        manager!!.setOverlayInterpolator(LinearInterpolator())
        adapter = CardStackAdapter(addList())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator = DefaultItemAnimator()
    }

    private fun paginate() {
        val old: List<ItemModel> = adapter!!.items
        val baru: ArrayList<Any?> = ArrayList<Any?>(addList())
        val callback = CardStackCallback(old, baru)
        val hasil = DiffUtil.calculateDiff(callback)
        adapter!!.items
        hasil.dispatchUpdatesTo(adapter!!)
    }

    private fun addList(): List<ItemModel> {
        val items: MutableList<ItemModel> = ArrayList<ItemModel>()
        items.add(ItemModel(R.drawable.d_img1, "Reni", "18 old", "Lampung"))
        items.add(ItemModel(R.drawable.d_img1, "Nur", "20 old", "Semarang"))
        items.add(ItemModel(R.drawable.d_img1, "Aulia", "22 old", "Bandung"))
        items.add(ItemModel(R.drawable.d_img1, "Ra", "19 old", "Lampung"))
        items.add(ItemModel(R.drawable.d_img1, "Mega", "25 old", "Jakarta"))
        items.add(ItemModel(R.drawable.d_img1, "Reni", "18 old", "Lampung"))
        return items
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }


}