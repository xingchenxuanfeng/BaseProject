package com.xc.baseproject

import android.app.Application
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI
import com.avos.avoscloud.AVOSCloud
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.xc.baseproject.analytics.AnalyticsManager
import com.xc.baseproject.constants.LEANCLOUD_APP_ID
import com.xc.baseproject.constants.LEANCLOUD_APP_Key
import com.xc.baseproject.hotpatch.HotpatchManager
import com.xc.baseproject.misc.ReleaseTree
import com.xc.baseproject.push.PushManager
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

const val aliyunAppKey = "28325271"
const val aliyunAppSecret = "f6bffa000b5f82927d1cab9db9d95be6"
const val aliyunAppRSASecret = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCTtAw2ovIewW+uYMlsDKrVow586bXwctsGgdTXfoBar+WAqbwts4vruhME7Ieuir5qZqEm5zRFzTDTorWeEdaxL7aoNFtdrXhdkiCsdZKZqvojOOsMaYlbG+lKe3j9Nx3XZE/2193wHX5LfxuuoxTuRoEAK73nhv5JCs1i1udgAp+IQyeS74Dv4tWkaxKrlhOS+CxlzgcyrX5sLDORo8f9sImAUtLf7pCnUtB/8necU7nOpxepU0LAVx0BH/5bwG9apUucjEtHlQinlYw6gsN0FsusVOJ/UIhTaDCEYOCDnWf8DkGSOC/RUveTLqLbPmVF77SEkaItm3QVG0jyHzJ9AgMBAAECggEBAI4ou5yWhlyNpxT5uMOTCZWnY5lcQ62SjjYcHaPu8FDYfRaxxxlhdulB5W5WzVxz00OLVc2gt9H65OS89PqXhsBxAOexxMJL5M6Ch4oVey0Fv8zIyonexcH4Ajia0F8+fi1CMiiNqeGiMV0ukJEsC0gueZiVgSjm57WbfQSFfJoNS4LtUA27NjzKuvWhlFZoL2ifbl5vfrnenlHIRro+5GfuFWT7Kv8hYwmi6YJpIHMnvB548/2JDCNKGQS/z6q1fmXgVciHuvEc+r6ZeYkYzrapA+YCbKYJPuxZBORj0fLtuEpIlXVL9UFSxOMEn3vI7toRYsqrYW4SjpMB6Vz6a6ECgYEA9iwEpntrcabYoLMOGsWQFdZ6ywbi35wXd0cfR1pOoPNFshLqeJ2f1VsVIfcm5RBlbdSHpFwY/Tzi22rxSU4u2BYrz28qbED0fwVSlBhbzBavppwICDe0gvMvYRpz7X+LCDF7haEBLRBxR/e5ipaWG+TPfKesBh2T8ugJYTFfZvUCgYEAmZmjZkG1EAMDm9AeFvNOCwM0zkQKGraMtXDYXuuZELk6frYv3r2OM8unmco2S9FDEf3xDzuHOP3Wk7IHWqGAZPsTvCkbSZAfUpsdFyeNf2rH6w8dIkKNjnqQ6nhoV++2IpKIkpgrRGYoNdDOAtmDtm6gq9sBQ5jSei1GY4KbGGkCgYEAy6XOkws272Zff1GhMXSOpgIwwxSHSwUORJaJwbVrpqqZ1Z7gabe8KfyXlumnO4g84IcZT1cBNT3RTkNCKKqNBiYRSCFmrvdtTEdS8UDVcBi0ZthdrK6onkwfvezsSmVg1v5YeUugdW9Mx4Sf8Y5xz9GR9EWhgc2mPyTF3anCiqECgYACr5cV4KxD3WmgveDo0dRZiPLRcIsksHCBc7m03zXL/xEwygrIQFROP49stwaACOa9V81xUL8qlfW8m25ZfV17ZpCPx8A8ZxLFJQU7NGvbAIkCaT8/LB7BkQ8bqoNYlk5/CETvG04WYhrt5frUPe15sTGKD8hT4EY98LC8aLpBkQKBgBN5/BsTsjcKmhVtvsKdibfUEl9tXkKIjrHwUC0R6z/LI3vTdj6JPZK8ZKCZnG3vPzcdiCZYDw/eH/OywlhO43tUrmRBxisdUYBUARfsV1GgDTH/ryRyofwmT+SWjzG4cH62TWrtacCKBoj77+/g/zcY5jBR2Jr7BpDpcj1U9Xd4"

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppUtil.application = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
        AVOSCloud.initialize(this, LEANCLOUD_APP_ID, LEANCLOUD_APP_Key)
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG)

        AnalyticsManager.init()
        PushManager.init(this)
        FeedbackAPI.init(this, aliyunAppKey, aliyunAppSecret)

        HotpatchManager.queryAndLoadNewPatch()
        RxJavaPlugins.setErrorHandler {
            Timber.e(it, "RxJavaException")
            throw it
        }

    }

}