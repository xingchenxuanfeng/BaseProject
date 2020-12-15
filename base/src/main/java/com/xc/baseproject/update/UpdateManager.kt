package com.xc.baseproject.update

import android.app.Activity
import com.vector.update_app.UpdateAppBean
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp
import com.xc.baseproject.AppUtil
import com.xc.baseproject.BuildConfig
import org.json.JSONObject

object UpdateManager {
    private const val cloudUpdateUrl = "https://code.aliyun.com/xingchenxf/covid-19-track/raw/master/lastestUpdate.json"

    private val urlSizeMap: MutableMap<String, Long> = mutableMapOf()

    fun checkUpdate(activity: Activity) {
        activity.updateApp(cloudUpdateUrl, UpdateAppHttpUtil()).check {
            this.parseJson { json ->
                val updateAppBean = UpdateAppBean()
                val jsonObject = JSONObject(json)
                updateAppBean
                        .setNewVersion(jsonObject.optString("new_version"))
                        .setApkFileUrl(jsonObject.optString("apk_file_url"))
                        .setTargetSize(jsonObject.optString("target_size"))
                        .setUpdateLog(jsonObject.optString("update_log"))
                        .setConstraint(jsonObject.optBoolean("constraint"))
                        .setNewMd5(jsonObject.optString("new_md5"))
                        .setOriginRes(json)
                        .update = checkWhetherUpdate(jsonObject)

                urlSizeMap[updateAppBean.apkFileUrl] = convertMBSizeToBitSize(updateAppBean.targetSize)

                return@parseJson updateAppBean
            }
        }

    }

    private fun convertMBSizeToBitSize(targetSize: String?): Long {
        if (targetSize == null) return 0

        val indexOfM = targetSize.indexOf("M")
        val mbSize = targetSize.substring(0, indexOfM).toLong()
        return mbSize * 1024 * 1024
    }

    fun getFileSizeByUrl(url: String): Long {
        return urlSizeMap[url] ?: 0
    }

    private fun checkWhetherUpdate(jsonObject: JSONObject): String {
        return if (jsonObject.optInt("new_version_code") > AppUtil.appVersionCode) "Yes" else "No"
    }

}