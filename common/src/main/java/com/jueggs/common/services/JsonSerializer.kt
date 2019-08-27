package com.jueggs.common.services

import com.jueggs.common.interfaces.Serializer
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import kotlinx.serialization.modules.getContextualOrDefault
import kotlin.reflect.KClass

@ImplicitReflectionSerializer
class JsonSerializer(
    private val json: Json
) : Serializer {
    override fun <T : Any> stringify(objects: List<T>, klass: KClass<T>): String = json.stringify(json.context.getContextualOrDefault(klass).list, objects)

    override fun <T : Any> parseList(objects: String, klass: KClass<T>): List<T> = json.parse(json.context.getContextualOrDefault(klass).list, objects)
}