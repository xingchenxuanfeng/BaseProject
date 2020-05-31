package com.taobao.alivfssdk.cache;

/**
 * 为了减少阿里云错信息增加的没用的类
 * todo delete
 */
@Deprecated
public interface IAVFSCache {
    interface OnObjectSetCallback {
        void onObjectSetCallback(String var1, boolean var2);
    }

    interface OnAllObjectRemoveCallback {
        void onAllObjectRemoveCallback(boolean var1);
    }

    interface OnObjectRemoveCallback {
        void onObjectRemoveCallback(String var1, boolean var2);
    }
}