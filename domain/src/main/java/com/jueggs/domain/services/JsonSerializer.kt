package com.jueggs.domain.services

import com.jueggs.domain.services.interfaces.ISerializer
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.list
import kotlinx.serialization.map
import kotlinx.serialization.modules.getContextualOrDefault
import kotlin.reflect.KClass

@ImplicitReflectionSerializer
class JsonSerializer(private val json: Json) : ISerializer {
    override fun <T : Any> stringify(objects: List<T>, klass: KClass<T>): String = json.stringify(json.context.getContextualOrDefault(klass).list, objects)

    override fun <T : Any> parseList(objects: String, klass: KClass<T>): List<T> = json.parse(json.context.getContextualOrDefault(klass).list, objects)

    override fun <T : Any> toJson(map: Map<T, Any>, klass: KClass<T>): JsonElement =
        json.toJson((json.context.getContextualOrDefault(klass) to StringSerializer).map, map.mapValues { it.value.toString() })
}