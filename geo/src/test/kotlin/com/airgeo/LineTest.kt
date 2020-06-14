package com.airgeo

import com.airgeo.method.Euclidean
import com.airgeo.method.Haversine
import com.airgeo.method.Rhumb
import org.junit.Assert
import org.junit.Test

/**
 * Tests for [Line]
 *
 * Created by Zenox on 10-6-2020 at 17:34.
 */
class LineTest {

    @Test
    fun `constructor » minimum`() {
        val a = Point()
        val b = Point()

        val line = Line(a, b)
        Assert.assertNotNull(line)
    }

    /* ***** [ real life ] ***** */

    @Test
    fun `distance » distance between Amsterdam and Groningen`() {
        val amsterdam = Point(latitude = 52.3, longitude = 4.88)
        val groningen = Point(latitude = 53.2, longitude = 6.55)

        val line = Line(amsterdam, groningen)
        Assert.assertEquals(150.48, line.getLength(Haversine()), ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(151.36, line.getLength(Rhumb()), ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(
            150.33,
            line.getLength(Euclidean()),
            ERROR_MARGIN_MEDIUM_DISTANCE
        ) // (~1300km shorter then haversine)
    }

    @Test
    fun `distance » distance between New York and Beijing`() {
        val newYork = Point(latitude = 40.65, longitude = -74.0) // 40 39' N 74 0 W
        val beijing = Point(latitude = 39.9, longitude = 116.35) // 39 54' N 116 21 E

        val line = Line(newYork, beijing)
        Assert.assertEquals(10_997.21, line.getLength(Haversine()), ERROR_MARGIN_LONG_DISTANCE)
        Assert.assertEquals(14_312.58, line.getLength(Rhumb()), ERROR_MARGIN_LONG_DISTANCE)
        Assert.assertEquals(
            9_679.22,
            line.getLength(Euclidean()),
            ERROR_MARGIN_LONG_DISTANCE
        ) // (~1300km shorter then haversine)
    }

    @Test
    fun `distance » distance between a and b`() {
        val a = Point(latitude = 0.0, longitude = 0.0)
        val b = Point(latitude = 10.0, longitude = 10.0)

        val line = Line(a, b)
        Assert.assertEquals(1568.52, line.getLength(Haversine()), ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(1572.53, line.getLength(Rhumb()), ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(
            1566.23,
            line.getLength(Euclidean()),
            ERROR_MARGIN_MEDIUM_DISTANCE
        )
    }

    /* ***** [ real life ] ***** */

    @Test
    fun `distance » distance between two arbitrary points not elevated`() {
        val pointA = Point(latitude = 50.0, longitude = 10.0)
        val pointB = Point(latitude = 51.0, longitude = 11.0)

        val line = Line(pointA, pointB)
        Assert.assertEquals(131.78, line.getLength(Haversine()), ERROR_MARGIN_LONG_DISTANCE) // ~130 km
        Assert.assertEquals(132.18, line.getLength(Rhumb()), ERROR_MARGIN_LONG_DISTANCE) // ~130 km
        Assert.assertEquals(131.66, line.getLength(Euclidean()), ERROR_MARGIN_LONG_DISTANCE) // ~130 km
    }

    @Test
    fun `distance » distance between two arbitrary points elevated`() {
        val pointA = Point(latitude = 50.0, longitude = 10.0)
        val pointB = Point(latitude = 51.0, longitude = 11.0, elevation = 1_000.0)

        val line = Line(pointA, pointB)
        Assert.assertEquals(131.78, line.getLength(Haversine()), ERROR_MARGIN_LONG_DISTANCE) // ~130 km
        Assert.assertEquals(132.18, line.getLength(Rhumb()), ERROR_MARGIN_LONG_DISTANCE) // ~130 km
        Assert.assertEquals(131.67, line.getLength(Euclidean()), ERROR_MARGIN_LONG_DISTANCE) // ~130 km
    }

}