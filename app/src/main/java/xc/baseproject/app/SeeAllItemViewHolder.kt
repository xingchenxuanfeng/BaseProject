package xc.baseproject.app

import com.xc.baseproject.app.R
import com.xc.baseproject.multiTypeAdapter.MultiBaseViewHolder
import com.xc.baseproject.multiTypeAdapter.MultiCommonViewHolder
import kotlinx.android.synthetic.main.holder_see_all_item.view.*
import org.greenrobot.eventbus.EventBus

class SeeAllItemViewHolder : MultiBaseViewHolder<SeeAllItemData>() {
    override fun getLayoutResource(): Int {
        return R.layout.holder_see_all_item
    }

    override fun onBindViewHolder(holder: MultiCommonViewHolder, item: SeeAllItemData) {

        holder.itemView.run {
            see_all_item_btn.setOnClickListener {
                item.action.run()
            }
        }
    }
}
