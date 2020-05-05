package xc.baseproject.app

import com.xc.baseproject.app.R
import com.xc.baseproject.multiTypeAdapter.MultiBaseViewHolder
import com.xc.baseproject.multiTypeAdapter.MultiCommonViewHolder
import kotlinx.android.synthetic.main.holder_china_area_city.view.*

class CityViewHolder : MultiBaseViewHolder<City>() {
    override fun getLayoutResource(): Int {
        return R.layout.holder_china_area_city
    }

    override fun onBindViewHolder(holder: MultiCommonViewHolder, item: City) {

        holder.itemView.run {
            cityName.text = item.cityName
            currentConfirmCount.text = item.currentConfirmedCount.toString()
            accumulateConfirmCount.text = item.confirmedCount.toString()
            deadCount.text = item.deadCount.toString()
            curedCount.text = item.curedCount.toString()

        }
    }
}
