package com.xc

import android.annotation.SuppressLint
import com.blankj.utilcode.util.ToastUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xc.baseproject.net.NetService
import io.reactivex.Observable
import retrofit2.http.GET
import timber.log.Timber
import java.io.Serializable

object Repository {
    interface GithubApi {
        @GET("master/testStaticData")
        fun getTestData(): Observable<TestData>
    }

    val githubApi: GithubApi by lazy {
        NetService.githubRetrofit.create(GithubApi::class.java)
    }

    @SuppressLint("CheckResult")
    fun addNewCompany(name: String) {//todo 优化或校验
        if (name.isNullOrEmpty()) {
            ToastUtils.showShort("公司名称不能为空")
            return
        }
        val newVoteModel = VoteModel(0, name, 1, true)
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("MainData")
        getMainData().subscribe {
            it.companyList.add(newVoteModel)
            reference.setValue(it)
        }
    }

    fun getMainData(): Observable<MainData> {
        return Observable.create {
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("MainData")
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.e(error.toException())
                    it.onError(error.toException())
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val mainData = dataSnapshot.getValue(MainData::class.java)
                    it.onNext(mainData ?: MainData())
                    it.onComplete()
                }
            })
        }
    }

}

data class TestData(val data: String) : Serializable
data class MainData(var companyList: MutableList<VoteModel> = mutableListOf()) : Serializable
data class VoteModel(
        var rank: Int = 0,
        var name: String = "***",
        var voteCount: Int = 0,
        var voteAction: Boolean? = null//true :顶 false :踩 null :无
) : Serializable