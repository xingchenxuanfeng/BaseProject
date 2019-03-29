package com.xc

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xc.baseproject.BaseActivity
import com.xc.baseproject.basefeature.R
import kotlinx.android.synthetic.main.main_996_activity.*

/**
 * @author xc
 * @time 19-3-29.
 */
class Main996Activity : BaseActivity() {

    private lateinit var adapter: MainAdapter

    private lateinit var data: MutableList<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_996_activity)

        sp_layout.setColorSchemeColors(Color.rgb(47, 223, 189))

        rv_main.layoutManager = LinearLayoutManager(getContext())

        data = mutableListOf<Any>()

        adapter = MainAdapter(data)

        rv_main.adapter

    }


}

class MainAdapter(data: MutableList<Any>?) : BaseQuickAdapter<Any, BaseViewHolder>(R.layout.item_996, data) {

    override fun convert(helper: BaseViewHolder, item: Any) {
        helper.setText(R.id.textView, item.toString())
    }

}