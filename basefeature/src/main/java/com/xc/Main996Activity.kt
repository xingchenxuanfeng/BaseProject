package com.xc

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xc.baseproject.BaseActivity
import com.xc.baseproject.basefeature.R
import kotlinx.android.synthetic.main.main_996_activity.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import timber.log.Timber

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

        initView()
        initData()
        initListener()


        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("MainData")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Timber.e("Listener was cancelled")
            }

            override fun onDataChange(p0: DataSnapshot) {
                Timber.e(p0.toString())
            }
        })
        val connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                if (connected) {
                    Timber.e("connected")
                } else {
                    Timber.e("not connected")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e("Listener was cancelled")
            }
        })

    }

    private fun initView() {
        initToolBar()
        sp_layout.setColorSchemeColors(Color.rgb(47, 223, 189))
        rv_main.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(getContext())
        data = mutableListOf()
        adapter = MultiTypeAdapter(data)
        adapter.register(VoteModel::class, MainViewHolder())
        rv_main.adapter = adapter
    }

    @SuppressLint("CheckResult")
    private fun initData() {
        Repository.getMainData().subscribe {
            data.clear()
            data.addAll(it.companyList)
            adapter.notifyDataSetChanged()
            sp_layout.isRefreshing = false
        }
    }

    private fun initListener() {
        sp_layout.setOnRefreshListener {
            initData()
        }
    }

    private fun initToolBar() {
        setActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
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

}