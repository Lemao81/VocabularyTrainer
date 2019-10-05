package com.jueggs.domain.services.interfaces

import kotlinx.serialization.json.JsonElement
import kotlin.reflect.KClass

interface ISerializer {
    fun <T : Any> stringify(objects: List<T>, klass: KClass<T>): String
    fun <T : Any> parseList(objects: String, klass: KClass<T>): List<T>
    fun <T : Any> toJson(map: Map<T, Any>, klass: KClass<T>): JsonElement
}