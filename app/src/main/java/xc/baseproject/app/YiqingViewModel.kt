package xc.baseproject.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xc.baseproject.extFunctions.addToDisposable
import com.xc.baseproject.extFunctions.netCompose
import com.xc.baseproject.net.NetService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber

class YiqingViewModel : ViewModel() {

    val yiqingLiveData: MutableLiveData<YiqingModel?> = MutableLiveData()
    var shareText: String = "超好用的冠状病毒疫情追踪App\nhttp://10528180.1891346914882850.functioncompute.com/yiqing/h5/sharepage"

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val yiqingNetService by lazy { NetService.aliYunRetrofit.create(YiqingNetService::class.java) }
    private val yiqingGitAliYunNetService by lazy { NetService.gitAliYunRetrofit.create(YiqingGitAliYunNetService::class.java) }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun updateData() {
        yiqingNetService.getInfo(false)
                .netCompose()
                .flatMap { data ->
                    if (data.updateTime != 0L && data.confirmedCount != 0L) {
                        Timber.d("get info from aliyun serverLess function successful")
                        return@flatMap Observable.just(data)
                    } else {
                        Timber.d("get info from aliyun serverLess function fail, use default git data")
                        return@flatMap yiqingGitAliYunNetService.getDefaultInfo().netCompose()
                    }
                }
                .subscribe({ it: YiqingModel? ->
                    yiqingLiveData.value = it
                }, {
                    yiqingLiveData.value = null
                })
                .addToDisposable(compositeDisposable)

        yiqingGitAliYunNetService.getShareInfo()
                .netCompose()
                .subscribe {
                    shareText = it?.shareText ?: shareText
                }
                .addToDisposable(compositeDisposable)
    }

    interface YiqingNetService {

        @GET("yiqing/getinfo")
        fun getInfo(@Query("raw") raw: Boolean): Observable<YiqingModel>
    }


    interface YiqingGitAliYunNetService {

        @GET("defaultInfo.json")
        fun getDefaultInfo(): Observable<YiqingModel>

        @GET("shareInfo.json")
        fun getShareInfo(): Observable<ShareInfo>
    }
}

