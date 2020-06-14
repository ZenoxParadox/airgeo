package com.airgeo.method

import com.airgeo.Point
import kotlin.math.*

/**
 * Measure method that converts the points into cartesian space (x, y, z).
 */
class Euclidean : MeasureMethod {

    override fun measure(from: Point, to: Point): Double {

        // Convert to cartesian coordinates
        val (x1, y1, z1) = getCartesian(from)
        val (x2, y2, z2) = getCartesian(to)

        // Euclidean distance formula
        return sqrt((x1 - x2).pow(2) + (y1 - y2).pow(2) + (z1 - z2).pow(2))
    }

    /**
     * Gives a [Point] along the way [from] [to] at the specified [distance]
     *
     * Notice: Considering the earth is an ellipsoid, when measuring from a one pole to another pole,
     * the result is likely to be completely off when interpolating using the Euclidean formula.
     *
     * Notice: Because [from] and [to] represent earth-locations and this measure-method lives in a
     * cartesian space, the results may be meaningless like in the earth; especially at large
     * distances!
     */
    override fun interpolate(from: Point, to: Point, distance: Double): Point {
        val totalDistance = measure(from, to)

        // Make sure we never overshoot
        if(distance > totalDistance){
            return to
        }

        val fraction = distance / totalDistance

        val latDelta = from.latitude - to.latitude
        val longDelta = from.longitude - to.longitude
        val elDelta = from.latitude - to.latitude

        val lat = from.latitude - latDelta * fraction
        val long = from.longitude - longDelta * fraction
        val el = from.elevation - elDelta * fraction

        return Point(lat, long, el)
    }

    /**
     * Converts the latitude and longitude into x, y, z coordinates of the given [point].
     * Returns a [Triple] where the values represent x, y, z.
     */
    fun getCartesian(point: Point): Triple<Double, Double, Double> {
        val radius = point.getWS84Radius()

        val latRadians = Math.toRadians(point.latitude)
        val longRadians = Math.toRadians(point.longitude)

        val x = radius * cos(latRadians) * cos(longRadians)
        val y = radius * cos(latRadians) * sin(longRadians)
        val z = radius * sin(latRadians)

        return Triple(x, y, z)
    }

}