package com.github.ivan_osipov.clabo.deserialization.strategies

import com.github.ivan_osipov.clabo.deserialization.annotations.Exclude
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

class AnnotationExclusionStrategy : ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        return false
    }

    override fun shouldSkipField(f: FieldAttributes?): Boolean {
        if(f == null) return false
        return f.getAnnotation(Exclude::class.java) != null
    }

}