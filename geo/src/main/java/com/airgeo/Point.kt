package com.airgeo

import com.airgeo.interfaces.Translatable
import com.airgeo.method.SEMI_MAJOR
import com.airgeo.method.SEMI_MINOR
import java.security.InvalidParameterException
import kotlin.math.*

val RANGE_LATITUDE = -90.0..90.0 // inclusive
val RANGE_LONGITUDE = -180.0..180.0 // exclusive

/**
 * Represents any location on (or above) Earth.
 */
data class Point(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val elevation: Double = 0.0 // in meters
) : Translatable<Point> {

    init {
        verifyLatitude(latitude)
        verifyLongitude(longitude)
    }

    override fun translate(vector: Vector): Point {
        val wrappedLatitude = wrapLatitude(latitude + vector.northbound)
        val wrappedLongitude = wrapLongitude(longitude + vector.eastbound)

        return Point(wrappedLatitude, wrappedLongitude, elevation + vector.distance)
    }

    fun verifyLatitude(latitude: Double) {
        if (latitude !in RANGE_LATITUDE) {
            throw InvalidParameterException("Invalid latitude (${latitude})")
        }
    }

    fun verifyLongitude(longitude: Double) {
        if (longitude < RANGE_LONGITUDE.start || longitude >= RANGE_LONGITUDE.endInclusive) {
            throw InvalidParameterException("Invalid longitude (${longitude})")
        }
    }

    /**
     * Gives the radius of the earth based on the [latitude] (in WGS84 ellipsoid earth-shape) in km.
     * This ellipse is smallest (semi-minor) at the poles and biggest (semi-major) at the equator.
     */
    fun getWS84Radius(): Double {
        val x = Math.toRadians(latitude)

        val r1 = SEMI_MAJOR
        val r2 = SEMI_MINOR

        val radius = sqrt(
            ((r1 * r1 * cos(x)).pow(2.0) + (r2 * r2 * sin(x)).pow(2.0))
                    /
                    ((r1 * cos(x)).pow(2.0) + (r2 * sin(x)).pow(2.0))
        )

        // return in km
        return (radius + elevation) / 1_000.0
    }

    /**
     * Returns the bearing in degrees (true-north) from this instance to [other].
     */
    fun getBearing(other: Point): Double {
        val φ1 = Math.toRadians(latitude)
        val λ1 = Math.toRadians(longitude)

        val φ2 = Math.toRadians(other.latitude)
        val λ2 = Math.toRadians(other.longitude)

        val y = sin(λ2-λ1) * cos(φ2)
        val x = cos(φ1)*sin(φ2) - sin(φ1)*cos(φ2)*cos(λ2-λ1)
        val θ = atan2(y, x)

        val bearing = (θ*180/Math.PI + 360) % 360 // in degrees

        return bearing
    }

    override fun equals(other: Any?): Boolean {
        other?.let {
            if (it is Point) {
                if (latitude == it.latitude && longitude == it.longitude) {
                    return true
                }
            }
        }

        return false
    }

}