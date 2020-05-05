package xc.baseproject.app

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.TimeUtils
import com.xc.baseproject.BaseActivity
import com.xc.baseproject.app.R
import com.xc.baseproject.extFunctions.toStringWithSign
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.activity_yiqing.*
import kotlinx.android.synthetic.main.china_accumulate_confirmed.*
import kotlinx.android.synthetic.main.china_cured.*
import kotlinx.android.synthetic.main.china_current_confirmed.*
import kotlinx.android.synthetic.main.china_dead.*
import kotlinx.android.synthetic.main.china_serious.*
import kotlinx.android.synthetic.main.china_suspected.*
import kotlinx.android.synthetic.main.global_accumulate_confirmed.*
import kotlinx.android.synthetic.main.global_cured.*
import kotlinx.android.synthetic.main.global_current_confirmed.*
import kotlinx.android.synthetic.main.global_dead.*
import me.drakeet.multitype.MultiTypeAdapter
import timber.log.Timber

class YiqingInfoActivity : BaseActivity() {
    private lateinit var yiqingViewModel: YiqingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yiqing)

        yiqingViewModel = ViewModelProvider(this)[YiqingViewModel::class.java]

        initObserver()

        yiqingViewModel.updateData()

    }

    private fun initObserver() {
        yiqingViewModel.yiqingLiveData.observe(this, Observer { data ->

            Timber.d("yiqing data:$data")

            bindStatusData(data)

            bindChinaAreaData(data)

            bindGlobalAreaData(data)
        })
    }

    private fun bindGlobalAreaData(data: YiqingModel) {
        val adapter = MultiTypeAdapter()

        val topList = data.globalAreaStat.subList(0, 9)
        val realList = mutableListOf<Any>()
        val seeAllItemData = SeeAllItemData(Action {
            realList.clear()
            realList.addAll(data.globalAreaStat)
            adapter.notifyDataSetChanged()
        })
        realList.addAll(topList)
        realList.add(seeAllItemData)

        adapter.items = realList
        adapter.register(GlobalAreaStat::class.java, GlobalAreaViewHolder())
        adapter.register(SeeAllItemData::class.java, SeeAllItemViewHolder())
        globalAreaDataRv.adapter = adapter

        globalAreaDataRv.layoutManager = LinearLayoutManager(getContext())
        globalAreaDataRv.setHasFixedSize(true)
        globalAreaDataRv.isNestedScrollingEnabled = false
    }

    private fun bindChinaAreaData(data: YiqingModel) {
        val adapter = MultiTypeAdapter()

        val topList = data.areaStat.subList(0, 9)
        val realList = mutableListOf<Any>()
        val seeAllItemData = SeeAllItemData(Action {
            realList.clear()
            realList.addAll(data.areaStat)
            adapter.notifyDataSetChanged()
        })
        realList.addAll(topList)
        realList.add(seeAllItemData)

        adapter.items = realList
        adapter.register(AreaStat::class.java, ChinaAreaViewHolder())
        adapter.register(SeeAllItemData::class.java, SeeAllItemViewHolder())
        chinaAreaDataRv.adapter = adapter

        chinaAreaDataRv.layoutManager = LinearLayoutManager(getContext())
        chinaAreaDataRv.setHasFixedSize(true)
        chinaAreaDataRv.isNestedScrollingEnabled = false
    }

    private fun bindStatusData(data: YiqingModel) {

        updateDataTime.text = TimeUtils.millis2String(data.updateTime)

        //国内
        china_current_confirmed.text = data.currentConfirmedCount.toString()
        china_current_confirmed_incr.text = data.currentConfirmedIncr.toStringWithSign()

        china_accumulate_confirmed.text = data.confirmedCount.toString()
        china_accumulate_confirmed_incr.text = data.confirmedIncr.toStringWithSign()

        china_suspected.text = data.suspectedCount.toString()
        china_suspected_incr.text = data.suspectedIncr.toStringWithSign()

        china_serious.text = data.seriousCount.toString()
        china_serious_incr.text = data.seriousIncr.toStringWithSign()

        china_cured.text = data.curedCount.toString()
        china_cured_incr.text = data.curedIncr.toStringWithSign()

        china_dead.text = data.deadCount.toString()
        china_dead_incr.text = data.deadIncr.toStringWithSign()

        //全球
        global_current_confirmed.text = data.globalStatistics.currentConfirmedCount.toString()
        global_current_confirmed_incr.text = data.globalStatistics.currentConfirmedIncr.toStringWithSign()

        global_accumulate_confirmed.text = data.globalStatistics.confirmedCount.toString()
        global_accumulate_confirmed_incr.text = data.globalStatistics.confirmedIncr.toStringWithSign()

        global_cured.text = data.globalStatistics.curedCount.toString()
        global_cured_incr.text = data.globalStatistics.curedIncr.toStringWithSign()

        global_dead.text = data.globalStatistics.deadCount.toString()
        global_dead_incr.text = data.globalStatistics.deadIncr.toStringWithSign()
    }
}