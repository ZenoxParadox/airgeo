package com.airgeo.method

import com.airgeo.ERROR_MARGIN_LONG_DISTANCE
import com.airgeo.ERROR_MARGIN_MEDIUM_DISTANCE
import com.airgeo.Point
import org.junit.Assert
import org.junit.Test

/**
 * Tests for [Haversine]
 *
 * Created by Zenox on 12-6-2020 at 14:11.
 */
@Deprecated(message = "Since the method is not really for the test, the test for it has also been deprecated.")
class HaversineTest {

    private val method = Haversine()

    @Test
    fun `measure » example 1`(){
        val a = Point(51.5007, 0.1246)
        val b  = Point(40.6892, 74.0445)

        Assert.assertEquals(5574.840456848555, method.measure(a, b), ERROR_MARGIN_LONG_DISTANCE)
    }

    @Test
    fun `measure » example 2`(){
        val a = Point(-0.116773, 51.510357)
        val b  = Point(-77.009003, 38.889931)

        Assert.assertEquals(8585.53, method.measure(a, b), ERROR_MARGIN_LONG_DISTANCE)
    }

    @Test
    fun `measure » example 3`(){
        val a = Point(36.12, -86.67)
        val b  = Point(33.94, -118.40)

        Assert.assertEquals(2886.44, method.measure(a, b), ERROR_MARGIN_LONG_DISTANCE)
    }

    /* ********** [ interpolate ] ********** */

    @Test
    fun `interpolate » short distance and using simple numbers`(){
        val a = Point(2.0, 2.0)
        val b = Point(3.0, 3.0)
        val traverse = 85.52

        // assert the distance is as expected
        Assert.assertEquals(157.17, method.measure(a, b), ERROR_MARGIN_MEDIUM_DISTANCE)

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

        Assert.assertEquals(39.67, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(-96.35, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `interpolate » romania to lithuania`() {
        val romania = Point(55.7, 21.8)
        val lithuania = Point(47.2, 27.0)
        val distance = 592.0

        val actual = method.interpolate(romania, lithuania, distance) // ~belarus

        Assert.assertEquals(50.74, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(25.07, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `interpolate » cambridge to paris`() {
        val cambridge = Point(52.205, 0.119)
        val paris = Point(48.857, 2.351)
        val distance = 101.0 // ~25%

        // Assert distance is as expected
        Assert.assertEquals(404.27, method.measure(cambridge, paris), ERROR_MARGIN_MEDIUM_DISTANCE)

        val actual = method.interpolate(cambridge, paris, distance)

        Assert.assertEquals(51.37, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.70, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

}