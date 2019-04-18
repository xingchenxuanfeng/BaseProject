package com.xc

import com.avos.avoscloud.*
import com.blankj.utilcode.util.ToastUtils
import com.xc.VoteModel.Companion.VOTE_STATE_NONE
import com.xc.baseproject.net.NetService
import io.reactivex.Observable
import org.greenrobot.eventbus.EventBus
import retrofit2.http.GET
import timber.log.Timber
import java.io.Serializable

object Repository {
    private const val CURRENT_VOTE = "currentVote"
    private const val CURRENT_VOTE_STATE = "currentVoteState"
    private var updatingData: Boolean = false

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
            query.cachePolicy = AVQuery.CachePolicy.NETWORK_ELSE_CACHE
            query.maxCacheAge = 24 * 3600 * 1000 //设置为一天，单位毫秒
            query.findInBackground(
                    object : FindCallback<VoteModel>() {
                        override fun done(avObjects: MutableList<VoteModel>?, avException: AVException?) {
                            if (avException != null) {
                                ToastUtils.showShort(avException.message)
                                return
                            }
                            val currentVoteModel: VoteModel? = AVUser.getCurrentUser()
                                    ?.getAVObject(CURRENT_VOTE, VoteModel::class.java)
                            val currentVoteState = AVUser.getCurrentUser().getInt(CURRENT_VOTE_STATE)
                            avObjects?.forEach { item: VoteModel ->
                                if (item == currentVoteModel) {
                                    item.voteState = currentVoteState
                                }
                            }

                            it.onNext(avObjects ?: mutableListOf())
                            it.onComplete()
                        }
                    }
            )

        }
    }

    private fun modifyVote(voteModel: VoteModel, oldVoteState: Int, newVoteState: Int) {
        voteModel.voteState = newVoteState
        voteModel.increment("voteCount", (newVoteState - oldVoteState))
        voteModel.isFetchWhenSave = true

        Timber.d("${voteModel.name}  $oldVoteState  $newVoteState ")
    }

    fun modifyMyVote(currentVote: VoteModel, newVoteState: Int) {
        if (updatingData) return ToastUtils.showShort("您操作的太频繁了")
        updatingData = true
        val currentUser = AVUser.getCurrentUser()
        val oldVote = currentUser.getAVObject(CURRENT_VOTE, VoteModel::class.java)
        val oldVoteState = currentUser.getInt(CURRENT_VOTE_STATE)
        if (currentVote != oldVote) {
            modifyVote(oldVote, oldVoteState, VOTE_STATE_NONE)
            EventBus.getDefault().post(ModifyVoteEvent(oldVote, VOTE_STATE_NONE))
        }
        modifyVote(currentVote, currentVote.voteState, newVoteState)

        val currentVoteState = currentVote.voteState
        currentUser.put(CURRENT_VOTE, currentVote)
        currentUser.put(CURRENT_VOTE_STATE, currentVoteState)
        currentUser.isFetchWhenSave = true
        AVObject.saveAllInBackground(mutableListOf(currentUser, oldVote), object : SaveCallback() {
            override fun done(e: AVException?) {
                updatingData = false
                val toast: String = if (oldVote != null && currentVote == oldVote) {
                    "投票成功"
                } else if (oldVote != null) {
                    "一位用户只能投票一次，自动撤销原投票"
                } else {
                    "投票成功"
                }
                ToastUtils.showShort(toast)
            }
        })
    }

    fun getCurrentVote(): VoteModel {
        val currentUser = AVUser.getCurrentUser()
        val voteModel = currentUser.getAVObject(CURRENT_VOTE, VoteModel::class.java)
        voteModel.voteState = currentUser.getInt(CURRENT_VOTE_STATE)
        return voteModel
    }
}

data class TestData(val data: String) : Serializable
@AVClassName("VoteModel")
class VoteModel : Serializable, AVObject() {
    var name: String
        get() = getString("name") ?: "未知公司"
        set(value) = put("name", value)
    var voteCount: Int
        get() = getInt("voteCount")
        set(value) = put("voteCount", value)
    var remoteVoteCount: Int
        get() = getInt("remoteVoteCount")
        set(value) = put("remoteVoteCount", value)

    var rank: Int = 0
    var voteState: Int = 0 //当前用户的操作，不需要保存到服务器

    companion object {
        const val VOTE_STATE_NONE = 0   // 无投票
        const val VOTE_STATE_UP = 1 //顶
        const val VOTE_STATE_DOWN = -1  // 踩
    }

    override fun toString(): String {
        return "VoteModel(name=$name, voteCount=$voteCount, voteState=$voteState, remoteVoteCount=$remoteVoteCount, rank=$rank)"
    }
}

class ModifyVoteEvent(val voteModelRef: VoteModel, val newVoteState: Int)