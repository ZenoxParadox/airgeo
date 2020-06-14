package com.airgeo.method

import com.airgeo.ERROR_MARGIN_LONG_DISTANCE
import com.airgeo.ERROR_MARGIN_MEDIUM_DISTANCE
import com.airgeo.Point
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Tests for [Euclidean]
 *
 *
 * Created by Zenox on 13-6-2020 at 19:10.
 */
class EuclideanTest {

    private val method = Euclidean()

    @Test
    fun `measure » simple numbers 1`(){
        val a = Point(0.0, 0.0)
        val b  = Point(0.0, 0.0)

        Assert.assertEquals(0.0, method.measure(a, b), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    /* ********** [ measure ] ********** */

    @Test
    fun `measure » example 1`(){
        val a = Point(51.5007, 0.1246)
        val b  = Point(40.6892, 74.0445)

        Assert.assertEquals(5395.36, method.measure(a, b), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `measure » example 2`(){
        val a = Point(-0.116773, 51.510357)
        val b  = Point(-77.009003, 38.889931)

        Assert.assertEquals(7946.73, method.measure(a, b), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `measure » example 3`(){
        val a = Point(36.12, -86.67)
        val b  = Point(33.94, -118.40)

        Assert.assertEquals(2861.87, method.measure(a, b), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    /* ********** [ interpolate ] ********** */

    @Test
    fun `interpolate » short distance and using simple numbers`(){
        val a = Point(2.0, 2.0)
        val b = Point(3.0, 3.0)
        val traverse = 85.52

        // assert the distance is as expected
        Assert.assertEquals(157.34, method.measure(a, b), ERROR_MARGIN_MEDIUM_DISTANCE)

        val actual = method.interpolate(a, b, traverse)

        Assert.assertEquals(2.54, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(2.54, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `interpolate » losAngeles to newYork`(){
        val losAngeles = Point(34.122222, -118.4111111)
        val newYork  = Point(40.66972222, -73.94388889)
        val traverse = 2050.0

        val actual = method.interpolate(losAngeles, newYork, traverse)

        Assert.assertEquals(37.57, actual.latitude, ERROR_MARGIN_LONG_DISTANCE)
        Assert.assertEquals(-94.96, actual.longitude, ERROR_MARGIN_LONG_DISTANCE)
    }

    @Test
    fun `interpolate » romania to lithuania`() {
        val romania = Point(55.7, 21.8)
        val lithuania = Point(47.2, 27.0)
        val distance = 592.0

        val actual = method.interpolate(romania, lithuania, distance) // ~belarus

        Assert.assertEquals(50.71, actual.latitude, ERROR_MARGIN_LONG_DISTANCE)
        Assert.assertEquals(24.85, actual.longitude, ERROR_MARGIN_LONG_DISTANCE)
    }

    @Test
    fun `interpolate » cambridge to paris`() {
        val cambridge = Point(52.205, 0.119)
        val paris = Point(48.857, 2.351)
        val distance = 101.0 // ~25%

        // Assert distance is as expected
        Assert.assertEquals(403.86, method.measure(cambridge, paris), ERROR_MARGIN_MEDIUM_DISTANCE)

        val actual = method.interpolate(cambridge, paris, distance)

        Assert.assertEquals(51.37, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.67, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    /* ********** [ getCartesian ] ********** */

    @Test
    fun `getCartesian » 0,0`() {
        val point = Point(0.0, 0.0)
        val (x, y, z) = method.getCartesian(point)

        Assert.assertEquals(6378.13, x, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, y, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, z, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `getCartesian » 0,90`() {
        val point = Point(0.0, 90.0)
        val (x, y, z) = method.getCartesian(point)

        Assert.assertEquals(0.0, x, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(6378.13, y, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, z, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `getCartesian » 90,0`() {
        val point = Point(90.0, 0.0)
        val (x, y, z) = method.getCartesian(point)

        Assert.assertEquals(0.0, x, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, y, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(6356.75, z, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `getCartesian » 45,45`() {
        val point = Point(45.0, 45.0)
        val (x, y, z) = method.getCartesian(point)

        Assert.assertEquals(3183.74, x, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(3183.74, y, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(4502.49, z, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `getCartesian » -45,-45`() {
        val point = Point(-45.0, -45.0)
        val (x, y, z) = method.getCartesian(point)

        Assert.assertEquals(3183.74, x, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(-3183.74, y, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(-4502.49, z, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

}