package com.airgeo.method

import com.airgeo.Point

/**
 * In 1984 World Geodetic System (WGS84) defined the earth as an ellipsoid where the poles differ
 * in dimension in respect to the equator. Around the poles this distance is defined as [SEMI_MINOR]
 * while the equators (where it is widest) is [SEMI_MAJOR].
 */
const val SEMI_MAJOR = 6_378_137.0
const val SEMI_MINOR = 6_356_752.3142

const val AVERAGE_RADIUS_OF_EARTH_KM = 6_371.0

/**
 * Contract for classes that are able to measure distances between [Point] instances.
 */
interface MeasureMethod {

    /**
     * measures the distance between [from] and [to] in kilometers.
     */
    fun measure(from: Point, to: Point): Double

    /**
     * returns an interpolated [Point] between [from] and [to] at the fraction (number between 0.0
     * and 1.0) given.
     */
    fun interpolate(from: Point, to: Point, distance: Double): Point

}