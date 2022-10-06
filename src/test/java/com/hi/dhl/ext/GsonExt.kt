@file:OptIn(ExperimentalContracts::class)
@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.hi.dhl.ktkit.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlin.contracts.ExperimentalContracts

/**
 * <pre>
 *     author: dhl
 *     date  : 2021/8/8
 *     desc  :
 * </pre>
 */
@kotlin.internal.InlineOnly
inline fun <T> Gson.typedToJson(src: T): String = toJson(src)

inline fun <reified T : Any> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

/**
 * 默认生成不包含 null 值的 JSON 串
 *
 * Example:
 *
 * ```
 * PeopleModel().toJson()
 * ```
 *
 * output: {"name":"dhl"}
 */
@kotlin.internal.InlineOnly
inline fun Any.toJson(): String = GsonBuilder().create().typedToJson(this)

/**
 * 生成包含 null 值的 JSON 串
 *
 * Example:
 *
 * ```
 * PeopleModel("dhl").toJson(true)
 * ```
 *
 * output: {"host":192.168.0.100,"name":"dhl"}
 */
@kotlin.internal.InlineOnly
inline fun Any.toJson(serializeNulls: Boolean): String =
    GsonBuilder()
        .serializeNulls()
        .create()
        .typedToJson(this)

/**
 * Example:
 *
 * ```
 * json.fromJson<PeopleModel>()
 * ```
 */
inline fun <reified T : Any> String.fromJson() =
    GsonBuilder().create().fromJson<T>(this)
