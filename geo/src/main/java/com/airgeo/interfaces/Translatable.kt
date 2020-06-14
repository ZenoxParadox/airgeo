package com.airgeo.interfaces

import com.airgeo.Vector
import kotlin.math.*

/**
 * Contract for classes that represent/contain a [com.airgeo.Point] and are able to translate
 * (as in: transformation) using an instance of [Vector].
 */
interface Translatable<T> {

    /**
     * Translates a given [Translatable] instance using the supplied [vector].
     */
    fun translate(vector: Vector): T

    /**
     * Makes sure the [latitude] wraps when crossing over a anti-maridean line.
     */
    fun wrapLatitude(latitude: Double): Double {
        val radians = Math.toRadians(latitude)
        val wrappedRadians = atan(sin(radians) / abs(cos(radians)))
        return Math.toDegrees(wrappedRadians)
    }

    /**
     * Makes sure the [longitude] wraps when crossing over a pole line.
     */
    fun wrapLongitude(longitude: Double): Double {
        val radians = Math.toRadians(longitude)
        val wrappedRadians = atan2(sin(radians), cos(radians))
        return Math.toDegrees(wrappedRadians)
    }

}