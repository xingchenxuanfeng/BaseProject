package com.xc

import android.os.Bundle
import com.xc.baseproject.BaseActivity
import com.xc.baseproject.basefeature.R
import kotlinx.android.synthetic.main.add_new_item_activity.*

class AddNewItemActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_item_activity)
        commit.setOnClickListener {
            Repository.addNewCompany(name_et.text.toString().trim())
            finish()
        }
    }

}
