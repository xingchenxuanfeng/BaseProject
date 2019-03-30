package com.xc

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.xc.baseproject.BaseActivity
import com.xc.baseproject.basefeature.R
import com.xc.baseproject.multiTypeAdapter.MultiBaseViewHolder
import com.xc.baseproject.multiTypeAdapter.MultiCommonViewHolder
import kotlinx.android.synthetic.main.item_996.view.*
import kotlinx.android.synthetic.main.main_996_activity.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import java.io.Serializable

/**
 * @author xc
 * @time 19-3-29.
 */
class Main996Activity : BaseActivity() {

    private lateinit var adapter: MultiTypeAdapter

    private lateinit var data: MutableList<VoteModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_996_activity)

        sp_layout.setColorSchemeColors(Color.rgb(47, 223, 189))

        rv_main.layoutManager = LinearLayoutManager(getContext())

        data = mutableListOf()

        data.add(VoteModel())
        data.add(VoteModel())
        data.add(VoteModel())
        data.add(VoteModel())
        data.add(VoteModel())

        adapter = MultiTypeAdapter(data)
        adapter.register(VoteModel::class, MainViewHolder())

        rv_main.adapter = adapter
    }
}

class MainViewHolder : MultiBaseViewHolder<VoteModel>() {
    override fun getLayoutResource(): Int {
        return R.layout.item_996
    }

    override fun onBindViewHolder(holder: MultiCommonViewHolder, item: VoteModel) {
        holder.itemView.run {
            rank_tv.text = item.rank.toString()
            name_tv.text = item.name
            up_ll.isSelected = (item.voteAction == true)
            down_ll.isSelected = (item.voteAction == false)
            up_ll.setOnClickListener {
                ToastUtils.showShort("顶")
                item.voteAction = if (item.voteAction != true) true else null
                adapter.notifyItemChanged(getPosition(holder))
            }
            down_ll.setOnClickListener {
                ToastUtils.showShort("踩")
                item.voteAction = if (item.voteAction != false) false else null
                adapter.notifyItemChanged(getPosition(holder))
            }
        }
    }
}

data class VoteModel(
        var rank: Int = 0,
        var name: String = "***",
        var voteCount: Int = 0,
        var voteAction: Boolean? = null//true :顶 false :踩 null :无
) : Serializable
