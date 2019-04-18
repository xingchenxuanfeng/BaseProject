package com.xc

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.xc.baseproject.BaseActivity
import com.xc.baseproject.basefeature.R
import kotlinx.android.synthetic.main.main_996_activity.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import org.greenrobot.eventbus.Subscribe

/**
 * @author xc
 * @time 19-3-29.
 */
class Main996Activity : BaseActivity() {

    private var data: MutableList<VoteModel> = mutableListOf()
    private var adapter: MultiTypeAdapter = MultiTypeAdapter(data)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_996_activity)
        initView()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun initView() {
        setTitle(R.string.main_label)
        sp_layout.setColorSchemeColors(Color.rgb(47, 223, 189))
        rv_main.layoutManager = LinearLayoutManager(getContext())
        adapter.register(VoteModel::class, MainViewHolder())
        rv_main.adapter = adapter
    }

    @SuppressLint("CheckResult")
    private fun refreshData() {
        sp_layout.isRefreshing = true
        Repository.getMainData().subscribe {
            data.clear()
            data.addAll(it)
            adapter.notifyDataSetChanged()
            sp_layout.isRefreshing = false
        }
    }

    private fun initListener() {
        sp_layout.setOnRefreshListener {
            refreshData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                startActivity(Intent(getContext(), AddNewItemActivity::class.java))
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main996menu, menu)
        return true
    }

    @Subscribe
    fun onEventModifyVote(event: ModifyVoteEvent) {
        for ((index, it) in data.withIndex()) {
            if (it == event.voteModelRef) {
                it.voteCount += event.newVoteState - it.voteState
                it.voteState = event.newVoteState
                adapter.notifyItemChanged(index)
            }
        }
    }
}