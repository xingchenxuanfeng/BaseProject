package xc.baseproject.app

import com.xc.baseproject.app.R
import com.xc.baseproject.multiTypeAdapter.MultiBaseViewHolder
import com.xc.baseproject.multiTypeAdapter.MultiCommonViewHolder
import kotlinx.android.synthetic.main.holder_global_area.view.*

class GlobalAreaViewHolder : MultiBaseViewHolder<GlobalAreaStat>() {
    override fun getLayoutResource(): Int {
        return R.layout.holder_global_area
    }

    override fun onBindViewHolder(holder: MultiCommonViewHolder, item: GlobalAreaStat) {

        holder.itemView.run {
            countryName.text = item.provinceName
            currentConfirmedCount.text = item.currentConfirmedCount.toString()

            deadRate.text = item.deadRate.toString()
            deadRateRank.text = (item.deadRateRank ?: "暂无").toString()
            deadCount.text = item.deadCount.toString()
            deadCountRank.text = (item.deadCountRank ?: "暂无").toString()
            confirmedCount.text = item.confirmedCount.toString()
            confirmedCountRank.text = (item.confirmedCountRank ?: "暂无").toString()


        }
    }
}
