package xc.baseproject.app

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xc.baseproject.BaseActivity
import com.xc.baseproject.app.R
import timber.log.Timber

class YiqingInfoActivity : BaseActivity() {
    private lateinit var yiqingViewModel: YiqingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yiqing)

        yiqingViewModel = ViewModelProvider(this)[YiqingViewModel::class.java]

        yiqingViewModel.yiqingLiveData.observe(this, Observer { data ->

            Timber.d("yiqing data:$data")
        })

        yiqingViewModel.updateData()

    }
}