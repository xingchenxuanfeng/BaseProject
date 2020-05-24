package com.xc.baseproject.update

import com.vector.update_app.HttpManager
import com.vector.update_app.HttpManager.FileCallback
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import java.io.File

/**
 * copy from https://github.com/WVector/AppUpdate
 * AppUpdate开源的默认实现，直接copy过来了，有需要的话改成去除OkHttpUtils的自定义实现
 * Created by Vector
 * on 2017/6/19 0019.
 */
class UpdateAppHttpUtil : HttpManager {
    /**
     * 异步get
     *
     * @param url      get请求地址
     * @param params   get参数
     * @param callBack 回调
     */
    override fun asyncGet(url: String, params: Map<String, String>, callBack: HttpManager.Callback) {
        OkHttpUtils.get()
                .url(url)
                .params(params)
                .build()
                .execute(object : StringCallback() {
                    override fun onError(call: Call, response: Response?, e: Exception?, id: Int) {
                        callBack.onError(validateError(e, response))
                    }

                    override fun onResponse(response: String?, id: Int) {
                        callBack.onResponse(response)
                    }
                })
    }

    /**
     * 异步post
     *
     * @param url      post请求地址
     * @param params   post请求参数
     * @param callBack 回调
     */
    override fun asyncPost(url: String, params: Map<String, String>, callBack: HttpManager.Callback) {
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(object : StringCallback() {
                    override fun onError(call: Call, response: Response?, e: Exception?, id: Int) {
                        callBack.onError(validateError(e, response))
                    }

                    override fun onResponse(response: String?, id: Int) {
                        callBack.onResponse(response)
                    }
                })
    }

    /**
     * 下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    override fun download(url: String, path: String, fileName: String, callback: FileCallback) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(object : FileCallBack(path, fileName, UpdateManager.getFileSizeByUrl(url)) {
                    override fun inProgress(progress: Float, total: Long, id: Int) {
                        callback.onProgress(progress, total)
                    }

                    override fun onError(call: Call, response: Response?, e: Exception?, id: Int) {
                        callback.onError(validateError(e, response))
                    }

                    override fun onResponse(response: File?, id: Int) {
                        callback.onResponse(response)
                    }

                    override fun onBefore(request: Request, id: Int) {
                        super.onBefore(request, id)
                        callback.onBefore()
                    }
                })
    }
}