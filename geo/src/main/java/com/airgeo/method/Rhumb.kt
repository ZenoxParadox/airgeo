package com.airgeo.method

import com.airgeo.Point
import kotlin.math.*

/**
 * A 'rhumb line' (or loxodrome) is a path of constant bearing, which crosses all meridians at the
 * same angle.
 */
@Deprecated(message = "For the exercise this method is deprecated", replaceWith = ReplaceWith("Euclidean"))
class Rhumb : MeasureMethod {

    /**
     * Gives the distance travelling from ‘this’ point to destination point along a rhumb line.
     */
    override fun measure(from: Point, to: Point): Double {
        val fromLatRadians = Math.toRadians(from.latitude)
        val toLatRadians = Math.toRadians(to.latitude)

        val deltaLat = toLatRadians - fromLatRadians

        var deltaLong = Math.toRadians(abs(to.longitude - from.longitude))

        // if dLon over 180° take shorter rhumb line across the anti-meridian:
        if (abs(deltaLong) > Math.PI) {
            if (deltaLong > 0) {
                deltaLong = -(2 * Math.PI - deltaLong)
            } else {
                deltaLong = (2 * Math.PI + deltaLong)
            }
        }

        // on Mercator projection, longitude distances shrink by latitude.
        // the stretch factor prevents this around Point(0, 0)
        val imageDelta = log(tan(toLatRadians / 2 + Math.PI / 4) / tan(fromLatRadians / 2 + Math.PI / 4), 0.0)

        val stretchFactor: Double
        if (abs(imageDelta) > 10e-12) {
            stretchFactor = deltaLat / imageDelta
        } else {
            stretchFactor = cos(fromLatRadians)
        }

        // distance is pythagoras on 'stretched' Mercator projection, √(Δφ² + q²·Δλ²)
        val distance = sqrt(deltaLat.pow(2) + stretchFactor.pow(2) * deltaLong.pow(2)) // angular distance in radians

        return distance * AVERAGE_RADIUS_OF_EARTH_KM
    }

    override fun interpolate(from: Point, to: Point, distance: Double): Point {
        throw NotImplementedError()
    }

}