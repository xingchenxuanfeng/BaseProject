package com.xc.testapp

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.xc.baseproject.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*
import kotlinx.android.synthetic.main.item.view.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(getContext())

        rv_sticky_header_container.init(TYPE_HEADER)
        rv_sticky_header_container.noHeaderPosition = 45

        rv.addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(getContext(), androidx.recyclerview.widget.DividerItemDecoration.VERTICAL))

        val data = Data.data
        rv.adapter = object : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
                return if (viewType == TYPE_HEADER) {
                    HeaderHolder(LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false))
                } else {
                    ContentHolder(LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false))
                }
            }

            override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
                if (data[position].type == TYPE_HEADER) {
                    holder.itemView.setBackgroundColor(Color.GREEN)
                } else {
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT)
                }

                holder.itemView.item_btn.text = data[position].content
                holder.itemView.setOnClickListener {
                    Toast.makeText(getContext(), "第${position}项 type=${data[position].type} text=${data[position].content}", Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({
                        Data.data.clear()
                        rv.adapter?.notifyDataSetChanged()
                    }, 1000)
                }
            }

            override fun getItemCount(): Int {
                return data.size
            }

            override fun getItemViewType(position: Int): Int {
                return data[position].type
            }
        }
    }

    class HeaderHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
    class ContentHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}
