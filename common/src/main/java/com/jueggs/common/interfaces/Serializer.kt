package com.jueggs.common.interfaces

import kotlin.reflect.KClass

interface Serializer {
    fun <T : Any> stringify(objects: List<T>, klass: KClass<T>): String
    fun <T : Any> parseList(objects: String, klass: KClass<T>): List<T>
}