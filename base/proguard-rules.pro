# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
#-printmapping proguardMapping.txt #移除该行，让firebase Crashlytics可自动上传映射文件  https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports?authuser=0
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
#继承activity,application,service,broadcastReceiver,contentprovider....不进行混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keep class **.R$* {
 *;
}

-keepclassmembers class * {
    void *(*Event);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#// natvie 方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


# support-v4
#https://stackoverflow.com/questions/18978706/obfuscate-android-support-v7-widget-gridlayout-issue
-dontwarn android.support.v4.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }


# support-v7
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }

# support design
#@link http://stackoverflow.com/a/31028536
#-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
#-------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#---------------------------------实体类-----------------------


#---------------------------------反射相关的类和方法-----------------------


#---------------------------------与js互相调用的类------------------------


#---------------------------------自定义View的类------------------------


#---------------------------------第三方库------------------------

#okhttp
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*
# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

-dontwarn okhttp3.**
-dontwarn org.conscrypt.**


#retrofit
# Retrofit does reflection on generic parameters and InnerClass is required to use Signature.
-keepattributes Signature, InnerClasses
# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions


# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }


#eventbus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

################gson##################
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

-keep public class * implements java.io.Serializable {*;}


#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


#Rxjava RxAndroid
-dontwarn rx.*
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# BRVAH
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}


#zxing
-keep class com.google.zxing.** {*;}
-dontwarn com.google.zxing.**

#firebase Crashlytics
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

#aliyun 推送
-keepclasseswithmembernames class ** {
    native <methods>;
}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-keep class com.ut.** {*;}
-keep class com.ta.** {*;}
-keep class anet.**{*;}
-keep class anetwork.**{*;}
-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.**{*;}
-keep class android.os.**{*;}
-keep class org.json.**{*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-dontwarn anet.**
-dontwarn org.android.spdy.**
-dontwarn org.android.agoo.**
-dontwarn anetwork.**
-dontwarn com.ut.**
-dontwarn com.ta.**

#aliyun 反馈
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**

#aliyun analytics
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

#aliyun hotfix
#基线包使用，生成mapping.txt
-printmapping mapping.txt
#生成的mapping.txt在app/build/outputs/mapping/release路径下，移动到/app路径下
#修复后的项目使用，保证混淆结果一致
#-applymapping mapping.txt
#hotfix
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
-dontwarn com.alibaba.sdk.android.utils.**
#防止inline
-dontoptimize
-keepclassmembers class * extends android.app.Application {
    public <init>();
}
#-keepclassmembers class com.my.pkg.MyRealApplication {
#    public <init>();
#}
# 如果不使用android.support.annotation.Keep则需加上此行
# -keep class com.my.pkg.SophixStubApplication$RealApplicationStub

# 小米通道
-keep class com.xiaomi.** {*;}
-dontwarn com.xiaomi.**
# 华为通道
-keep class com.huawei.** {*;}
-dontwarn com.huawei.**
# GCM/FCM通道
-keep class com.google.firebase.**{*;}
-dontwarn com.google.firebase.**
# OPPO通道
-keep public class * extends android.app.Service
# VIVO通道
-keep class com.vivo.** {*;}
-dontwarn com.vivo.**
# 魅族通道
-keep class com.meizu.cloud.** {*;}
-dontwarn com.meizu.cloud.**

#keep crashreporter
-keep class com.alibaba.motu.crashreporter.MotuCrashReporter{ *;}
-keep class com.alibaba.motu.crashreporter.ReporterConfigure{*;}
-keep class com.alibaba.motu.crashreporter.utrestapi.UTRestReq{*;}
-keep interface com.alibaba.motu.crashreporter.IUTCrashCaughtListener{*;}
-keep interface com.alibaba.motu.crashreporter.ICrashReportSendListener{*;}
-keep interface com.alibaba.motu.crashreporter.ICrashReportDataListener{*;}
-keep interface com.ut.mini.crashhandler.*{*;}
-keep class com.uc.crashsdk.**{*;}
-keep class com.alibaba.motu.crashreporter.YouKuCrashReporter{public *;}
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

#keep apm
-keep class com.taobao.monitor.APMLauncher{*;}
-keep class com.taobao.monitor.impl.logger.Logger{*;}
-keep class com.taobao.monitor.impl.logger.IDataLogger{*;}
-keep class com.taobao.monitor.impl.data.AbsWebView{*;}
-keep class com.taobao.monitor.impl.data.GlobalStats{*;}
-keep class com.taobao.monitor.impl.common.Global{*;}
-keep class com.taobao.monitor.impl.data.WebViewProxy{*;}
-keep class com.taobao.monitor.impl.logger.Logger{*;}
-keep class com.taobao.monitor.impl.processor.pageload.IProcedureManager{*;}
-keep class com.taobao.monitor.impl.processor.pageload.ProcedureManagerSetter{*;}
-keep class com.taobao.monitor.impl.util.TimeUtils{*;}
-keep class com.taobao.monitor.impl.util.TopicUtils{*;}
-keep class com.taobao.monitor.impl.common.DynamicConstants{*;}
-keep class com.taobao.application.common.data.DeviceHelper{*;}
-keep class com.taobao.application.common.impl.AppPreferencesImpl{*;}
-keep class com.taobao.monitor.impl.processor.launcher.PageList{*;}
-keep class com.taobao.monitor.impl.processor.fragmentload.FragmentInterceptorProxy{*;}
-keep class com.taobao.monitor.impl.processor.fragmentload.IFragmentInterceptor{*;}
-keep class com.taobao.monitor.impl.logger.DataLoggerUtils{*;}
-keep interface com.taobao.monitor.impl.data.IWebView{*;}
-keep interface com.taobao.monitor.impl.processor.IProcessor{*;}
-keep interface com.taobao.monitor.impl.processor.IProcessorFactory{*;}
-keep interface com.taobao.monitor.impl.logger.IDataLogger{*;}
-keep interface com.taobao.monitor.impl.trace.IDispatcher{*;}
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

#keep tlog
-keep interface com.taobao.tao.log.ITLogController{*;}
-keep class com.taobao.tao.log.upload.*{*;}
-keep class com.taobao.tao.log.message.*{*;}
-keep class com.taobao.tao.log.LogLevel{*;}
-keep class com.taobao.tao.log.TLog{*;}
-keep class com.taobao.tao.log.TLogConstant{*;}
-keep class com.taobao.tao.log.TLogController{*;}
-keep class com.taobao.tao.log.TLogInitializer{public *;}
-keep class com.taobao.tao.log.TLogUtils{public *;}
-keep class com.taobao.tao.log.TLogNative{*;}
-keep class com.taobao.tao.log.TLogNative$*{*;}
-keep class com.taobao.tao.log.CommandDataCenter{*;}
-keep class com.taobao.tao.log.task.PullTask{*;}
-keep class com.taobao.tao.log.task.UploadFileTask{*;}
-keep class com.taobao.tao.log.upload.LogFileUploadManager{public *;}
-keep class com.taobao.tao.log.monitor.**{*;}
#兼容godeye
-keep class com.taobao.tao.log.godeye.core.module.*{*;}
-keep class com.taobao.tao.log.godeye.GodeyeInitializer{*;}
-keep class com.taobao.tao.log.godeye.GodeyeConfig{*;}
-keep class com.taobao.tao.log.godeye.core.control.Godeye{*;}
-keep interface com.taobao.tao.log.godeye.core.GodEyeAppListener{*;}
-keep interface com.taobao.tao.log.godeye.core.GodEyeReponse{*;}
-keep interface com.taobao.tao.log.godeye.api.file.FileUploadListener{*;}
-keep public class * extends com.taobao.android.tlog.protocol.model.request.base.FileInfo{*;}
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod