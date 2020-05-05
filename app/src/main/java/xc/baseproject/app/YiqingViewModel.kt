package xc.baseproject.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xc.baseproject.extFunctions.addToDisposable
import com.xc.baseproject.extFunctions.netCompose
import com.xc.baseproject.net.NetService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class YiqingViewModel : ViewModel() {

    val yiqingLiveData: MutableLiveData<YiqingModel> = MutableLiveData()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val yiqingNetService by lazy { NetService.testRetrofit.create(YiqingNetService::class.java) }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun updateData(): Unit {
        yiqingNetService.getInfo(false)
                .netCompose()
                .subscribe {
                    yiqingLiveData.value = it
                }
                .addToDisposable(compositeDisposable)
    }

    interface YiqingNetService {

        @GET("2016-08-15/proxy/yiqing/getinfo")
        fun getInfo(@Query("raw") raw: Boolean): Observable<YiqingModel>
    }
}

