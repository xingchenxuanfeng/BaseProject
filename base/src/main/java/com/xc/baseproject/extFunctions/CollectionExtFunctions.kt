package com.xc.baseproject.extFunctions

fun <T> MutableCollection<in T>.addAllIfNotNull(elements: Iterable<T>?): Boolean {
    if (elements != null) {
        return this.addAll(elements)
    }
    return false
}