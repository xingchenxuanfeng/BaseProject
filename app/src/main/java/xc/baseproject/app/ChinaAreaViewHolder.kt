package xc.baseproject.app

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xc.baseproject.app.R
import com.xc.baseproject.extFunctions.setVisible
import com.xc.baseproject.multiTypeAdapter.MultiBaseViewHolder
import com.xc.baseproject.multiTypeAdapter.MultiCommonViewHolder
import kotlinx.android.synthetic.main.holder_china_area_province.view.*
import me.drakeet.multitype.MultiTypeAdapter

class ChinaAreaViewHolder : MultiBaseViewHolder<AreaStat>() {
    override fun getLayoutResource(): Int {
        return R.layout.holder_china_area_province
    }

    override fun onBindViewHolder(holder: MultiCommonViewHolder, item: AreaStat) {

        holder.itemView.run {
            provinceName.text = item.provinceShortName
            currentConfirmCount.text = item.currentConfirmedCount.toString()
            accumulateConfirmCount.text = item.confirmedCount.toString()
            deadCount.text = item.deadCount.toString()
            curedCount.text = item.curedCount.toString()

            provinceName.setOnClickListener {
                if (citiesRv.isShown) {
                    citiesRv.setVisible(false)
                    provinceName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more_15dp, 0)
                } else {
                    citiesRv.setVisible(true)
                    provinceName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less_15dp, 0)

                    bindCitiesData(citiesRv, item.cities)
                }
            }
        }
    }

    private fun bindCitiesData(recyclerView: RecyclerView, cities: List<City>) {
        val adapter = MultiTypeAdapter()

        adapter.items = cities
        adapter.register(City::class.java, CityViewHolder())
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false
    }

}
