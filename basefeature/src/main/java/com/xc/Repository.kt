package com.xc

import com.avos.avoscloud.*
import com.blankj.utilcode.util.ToastUtils
import com.xc.baseproject.net.NetService
import io.reactivex.Observable
import retrofit2.http.GET
import java.io.Serializable

object Repository {
    private const val CURRENT_VOTE_KEY = "currentVote"
    private const val CURRENT_VOTE_COUNT = "currentVoteCount"

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
                            avObjects?.forEach { item: VoteModel ->
                                val currentVoteModel = AVUser.getCurrentUser().getAVObject(CURRENT_VOTE_KEY, VoteModel::class.java)
                                if (item == currentVoteModel) {
                                    item.upAction = currentVoteModel.upAction
                                    item.downAction = currentVoteModel.downAction
                                }
                            }

                            it.onNext(avObjects ?: mutableListOf())
                            it.onComplete()
                        }
                    }
            )

        }
    }

    fun modifyMyVote(voteModel: VoteModel) {
        val currentUser = AVUser.getCurrentUser()
        val oldVote = currentUser.get(CURRENT_VOTE_KEY)
        val oldVoteCount = currentUser.getInt(CURRENT_VOTE_COUNT)
        val currentVoteCount =
                when {
                    voteModel.upAction -> 1
                    voteModel.downAction -> -1
                    else -> 0
                }
        currentUser.put(CURRENT_VOTE_KEY, voteModel)
        currentUser.put(CURRENT_VOTE_COUNT, currentVoteCount)
        currentUser.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                val toast: String = if (oldVote != null && voteModel == oldVote) {
                    "投票成功"
                } else if (oldVote != null) {
                    "您已经投票过，一位用户只能投票一次，自动删除原投票内容"
                } else {
                    "投票成功"
                }
                ToastUtils.showShort(toast)
            }
        })
        val voteCount = voteModel.voteCount ?: 1
        voteModel.voteCount = voteCount + (currentVoteCount - oldVoteCount)
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