package com.xc.testapp

import android.content.Context
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout

/**
 * @author xc
 * @time 18-12-5.
 */
class RvStickyHeaderContainer : FrameLayout {
    private val debug = Log.isLoggable("RvStickyHeaderContainer", Log.DEBUG)
    private val tag = "RvStickyHeaderContainer"
    private lateinit var headerContainer: FrameLayout
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    private var headerType: Int = 0
    var noHeaderPosition: Int = Int.MAX_VALUE

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun init(headerType: Int) {
        this.headerType = headerType
        recyclerView = getChildAt(0) as? androidx.recyclerview.widget.RecyclerView
                ?: throw RuntimeException("RecyclerView should be the first child view.")

        headerContainer = FrameLayout(context)
        headerContainer.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        addView(headerContainer)

        recyclerView.addOnScrollListener(RvOnScrollListener())
    }

    private inner class RvOnScrollListener : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
        var currentStickyHeaderPosition = -1

        override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {

            super.onScrolled(recyclerView, dx, dy)
            val adapter = recyclerView?.adapter
                    ?: throw RuntimeException("Please set adapter")
            val layoutManager = recyclerView.layoutManager as? androidx.recyclerview.widget.LinearLayoutManager
                    ?: throw RuntimeException("Only support LinearLayoutManager")

            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val relativeStickyHeaderPosition = findRelativeStickyHeaderPosition(firstVisibleItemPosition, adapter)

            //初始化header的viewHolder
            if (currentStickyHeaderPosition != relativeStickyHeaderPosition) {
                currentStickyHeaderPosition = relativeStickyHeaderPosition
                if (relativeStickyHeaderPosition == -1) {
                    stickyHeaderViewHolder.itemView.visibility = View.GONE
                    if (debug) Log.d(tag, "relativeStickyHeader removed")
                } else {
                    stickyHeaderViewHolder.itemView.visibility = View.VISIBLE
                    adapter.bindViewHolder(stickyHeaderViewHolder, relativeStickyHeaderPosition)
                }
            }

            if (relativeStickyHeaderPosition == -1) {
                if (debug) Log.d(tag, "not found relativeStickyHeader")
                return
            }

            val nextStickyHeaderPosition = findNextStickyHeaderPosition(firstVisibleItemPosition, adapter)

            //设置了不显示header的position的情况
            val lastShowHeaderPosition = Math.max(0, noHeaderPosition - 1)

            val nextStickyHeaderOrLastHeaderPosition = Math.min(nextStickyHeaderPosition, lastShowHeaderPosition)
            val nextStickyHeaderOrLastHeaderView: View? = layoutManager.findViewByPosition(nextStickyHeaderOrLastHeaderPosition)

            val nextStickyHeaderOrLastHeaderViewTop = (nextStickyHeaderOrLastHeaderView?.top
                    ?: if (nextStickyHeaderOrLastHeaderPosition < firstVisibleItemPosition) 0 else recyclerView.height)

            val realStickHeaderView = stickyHeaderViewHolder.itemView
            val translationY = nextStickyHeaderOrLastHeaderViewTop - realStickHeaderView.height

            if (translationY > 0) {
                //保持顶部
                realStickHeaderView.translationY = 0.toFloat()
            } else {
                //随着下个header偏移
                realStickHeaderView.translationY = translationY.toFloat()
            }

            if (debug) Log.d(tag, "firstVisibleItemPosition=$firstVisibleItemPosition relativeStickyHeaderPosition=$relativeStickyHeaderPosition nextStickyHeaderPosition=$nextStickyHeaderPosition lastShowHeaderPosition=$lastShowHeaderPosition realStickHeaderView.height=${realStickHeaderView.height} realStickHeaderView.translationY=${realStickHeaderView.translationY}")

        }
    }

    private val stickyHeaderViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder by lazy {
        val viewHolder = recyclerView.adapter?.createViewHolder(headerContainer, headerType)
                ?: throw RuntimeException("Please set adapter")
        Handler().postAtFrontOfQueue {
            headerContainer.addView(viewHolder.itemView)
        }
        viewHolder
    }

    private fun findRelativeStickyHeaderPosition(firstVisibleItemPosition: Int, adapter: androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>): Int {
        for (index in firstVisibleItemPosition downTo 0) {
            val itemViewType = adapter.getItemViewType(index)
            if (itemViewType == headerType) {
                return index
            }
        }
        return -1
    }

    private fun findNextStickyHeaderPosition(firstVisibleItemPosition: Int, adapter: androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>): Int {
        for (index in firstVisibleItemPosition + 1 until adapter.itemCount) {
            val itemViewType = adapter.getItemViewType(index)
            if (itemViewType == headerType) {
                return index
            }
        }
        return adapter.itemCount - 1
    }
}

