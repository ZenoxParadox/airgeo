package com.airgeo

import com.airgeo.method.Euclidean
import com.airgeo.method.Haversine
import com.airgeo.method.Rhumb
import org.junit.Assert
import org.junit.Test

/**
 * Tests for [PolyLine]
 *
 * Created by Zenox on 11-6-2020 at 14:18.
 */
class PolyLineTest {

    @Test(expected = IllegalArgumentException::class)
    fun `constructor » should throw exception when not having enough points`() {
        val list = listOf<Point>() // no items
        PolyLine(list)

        // no assert
    }

    @Test
    fun `constructor » should have the correct amount of lines for 4 points`() {
        val a = Point(0.0, 0.0)
        val b = Point(1.0, 1.0)
        val c = Point(2.0, 2.0)
        val d = Point(3.0, 3.0)

        val list = listOf(a, b, c, d)
        val polyline = PolyLine(list)

        Assert.assertEquals(3, polyline.getLines().size)
    }

    @Test
    fun `constructor » should have the correct amount of lines 51 points`() {
        val list = mutableListOf<Point>()

        for (i in 0..50) {
            val item = Point(i.toDouble(), i.toDouble())
            list.add(item)
        }

        val polyline = PolyLine(list)

        // Notice 51 items -> 50 lines!
        Assert.assertEquals(50, polyline.getLines().size)
    }


    /* ***** [ length ] ***** */

    @Test
    fun `length » adds a load of points`() {
        val list = mutableListOf<Point>()

        for (i in 0..10) {
            val item = Point(i.toDouble(), i.toDouble())
            list.add(item)
        }

        val polyline = PolyLine(list)

        Assert.assertEquals(1568.55, polyline.getLength(Haversine()), ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(1569.13, polyline.getLength(Rhumb()), ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(1570.22, polyline.getLength(Euclidean()), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    /* ***** [ length ] ***** */

    @Test
    fun `getPosition » should return the last point in the line when overshooting`() {
        val list = mutableListOf<Point>()

        for (i in 0..3) {
            val item = Point(i.toDouble(), i.toDouble())
            list.add(item)
        }

        val polyline = PolyLine(list)

        val actual = polyline.getPoint(4_000.0, Euclidean())

        Assert.assertEquals(3.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(3.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `getPosition » should ignore negative distance value`() {
        val list = mutableListOf<Point>()

        for (i in 0..3) {
            val item = Point(i.toDouble(), i.toDouble())
            list.add(item)
        }

        val polyline = PolyLine(list)

        val actual = polyline.getPoint(-4_000.0, Haversine())

        Assert.assertEquals(3.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(3.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `getPosition » should select correct line segment and traverse down it`() {
        val list = mutableListOf<Point>()

        for (i in 0..10) {
            val item = Point(i.toDouble(), i.toDouble())
            list.add(item)
        }

        val polyline = PolyLine(list)

        val actual = polyline.getPoint(400.0, Haversine())

        Assert.assertEquals(2.54, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(2.54, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, actual.elevation, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `getPosition » should give approximate result `() {

        // case comes from Doctor Rick from The Math Forum

        val losAngeles = Point(34.122222, -118.4111111)
        val newYork  = Point(40.66972222, -73.94388889)
        val list = listOf(losAngeles, newYork)
        val polyline = PolyLine(list)

        val actual = polyline.getPoint(2050.0, Haversine())

        Assert.assertEquals(39.67, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(-96.35, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

}