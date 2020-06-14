package com.airgeo.method

import com.airgeo.Line
import com.airgeo.Point
import kotlin.math.*

/**
 * Method that assumes the shape of the earth to be an Ellipsoid and measures distances
 * 'as-the-crow-flies' (i.e: following the curvature of the earth).
 */
@Deprecated(
    message = "For the exercise this method is deprecated",
    replaceWith = ReplaceWith("Euclidean")
)
class Haversine : MeasureMethod {

    /**
     * Get the distance in KM as a straight line between the two points that make out this [Line] using
     * the Haversine formula.
     */
    override fun measure(from: Point, to: Point): Double {

        val fromRadians = Math.toRadians(from.latitude)
        val toRadians = Math.toRadians(to.latitude)

        val latDelta = Math.toRadians(from.latitude - to.latitude)
        val longDelta = Math.toRadians(from.longitude - to.longitude)

        val x =
            sin(latDelta / 2) * sin(latDelta / 2) +
                    (cos(fromRadians) * cos(toRadians) *
                            sin(longDelta / 2) * sin(longDelta / 2))

        val distance = 2 * atan2(sqrt(x), sqrt(1 - x))

        return AVERAGE_RADIUS_OF_EARTH_KM * distance
    }

    /**
     * Gives [Point] at given fraction between [from] and [to] and given point.
     */
    override fun interpolate(from: Point, to: Point, distance: Double): Point {
        val totalDistance = measure(from, to)
        val fraction = distance / totalDistance

        val fromLatRadians = Math.toRadians(from.latitude)
        val fromLongRadians = Math.toRadians(from.longitude)

        val toLatRadians = Math.toRadians(to.latitude)
        val toLongRadians = Math.toRadians(to.longitude)

        val latDelta = toLatRadians - fromLatRadians
        val longDelta = toLongRadians - fromLongRadians
        val a = sin(latDelta / 2) * sin(latDelta / 2) + cos(fromLatRadians) * cos(toLatRadians) * sin(longDelta / 2) * sin(longDelta / 2)
        val d = 2 * atan2(sqrt(a), sqrt(1 - a))

        val A = sin((1 - fraction) * d) / sin(d)
        val B = sin(fraction * d) / sin(d)

        val x = A * cos(fromLatRadians) * cos(fromLongRadians) + B * cos(toLatRadians) * cos(toLongRadians)
        val y = A * cos(fromLatRadians) * sin(fromLongRadians) + B * cos(toLatRadians) * sin(toLongRadians)
        val z = A * sin(fromLatRadians) + B * sin(toLatRadians)

        val pointLatRadians = atan2(z, sqrt(x * x + y * y))
        val pointLongRadians = atan2(y, x)

        return Point(
            latitude = Math.toDegrees(pointLatRadians),
            longitude = Math.toDegrees(pointLongRadians)
        )
    }

}