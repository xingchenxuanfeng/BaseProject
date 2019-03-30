package com.xc.baseproject.multiTypeAdapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.drakeet.multitype.ItemViewBinder

abstract class MultiBaseViewHolder<T> : ItemViewBinder<T, MultiCommonViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): MultiCommonViewHolder {
        return MultiCommonViewHolder(inflater.inflate(getLayoutResource(), parent, false))
    }

    @LayoutRes
    abstract fun getLayoutResource(): Int

    abstract override fun onBindViewHolder(holder: MultiCommonViewHolder, item: T)
}

class MultiCommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)