package com.xc

import android.annotation.SuppressLint
import com.avos.avoscloud.*
import com.blankj.utilcode.util.ToastUtils
import com.xc.baseproject.net.NetService
import io.reactivex.Observable
import retrofit2.http.GET
import timber.log.Timber
import java.io.Serializable

object Repository {
    init {
        AVObject.registerSubclass(VoteModel::class.java)
    }

    interface GithubStaticDataApi {
        @GET("master/testStaticData")
        fun getTestData(): Observable<TestData>
    }

    val githubApi: GithubStaticDataApi by lazy {
        NetService.githubRetrofit.newBuilder().baseUrl("https://raw.githubusercontent.com/xingchenxuanfeng/staticData/").build().create(GithubStaticDataApi::class.java)
    }

    fun addNewCompany(name: String?) {
        if (name.isNullOrEmpty()) {
            ToastUtils.showShort("公司名称不能为空")
            return
        }
        val voteModel = VoteModel()
        voteModel.name = name
        voteModel.voteCount = 1

        voteModel.saveInBackground(
                object : SaveCallback() {
                    override fun done(e: AVException?) {
                        ToastUtils.showShort(e?.message ?: "保存成功")
                    }
                }
        )
    }

    fun getMainData(): Observable<List<VoteModel>> {
        return Observable.create {
            val query = AVObject.getQuery(VoteModel::class.java) //AVQuery<VoteModel>("VoteModel")
            query.findInBackground(
                    object : FindCallback<VoteModel>() {
                        override fun done(avObjects: MutableList<VoteModel>?, avException: AVException?) {
                            if (avException != null) {
                                ToastUtils.showShort(avException.message)
                                return
                            }
                            it.onNext(avObjects ?: mutableListOf())
                            it.onComplete()
                        }
                    }
            )

        }
    }
}

data class TestData(val data: String) : Serializable
@AVClassName("VoteModel")
class VoteModel : Serializable, AVObject() {
    var name: String? = ""
    var rank: Int? = 0
    var voteCount: Int? = 1
    var upAction: Boolean = false
    var downAction: Boolean = false
}