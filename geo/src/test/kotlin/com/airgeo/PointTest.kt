package com.airgeo

import org.junit.Assert
import org.junit.Test
import java.security.InvalidParameterException

/**
 * Tests for [Point]
 *
 * Created by Zenox on 9-6-2020 at 16:09.
 */
class PointTest {

    @Test
    fun `constructor » empty`() {
        val point = Point()
        Assert.assertNotNull(point)
    }

    @Test
    fun `constructor » min latitude`() {
        val point = Point(latitude = -90.0)
        Assert.assertNotNull(point)
    }

    @Test
    fun `constructor » max latitude`() {
        val point = Point(latitude = 90.0)
        Assert.assertNotNull(point)
    }

    @Test
    fun `constructor » min longitude`() {
        val point = Point(longitude = -180.0)
        Assert.assertNotNull(point)
    }

    @Test
    fun `constructor » max longitude`() {
        val point = Point(longitude = 179.99)
        Assert.assertNotNull(point)
    }

    @Test(expected = InvalidParameterException::class)
    fun `constructor » exclusiveness longitude`() {
        val point = Point(longitude = 180.0)
        Assert.assertNotNull(point)
    }

    @Test
    fun `constructor » latitude » negative boundary`() {
        val point = Point(-90.0, .0, .0)
        Assert.assertNotNull(point)
    }

    @Test
    fun `constructor » latitude » positive boundary`() {
        val point = Point(90.0, .0, .0)
        Assert.assertNotNull(point)
    }

    @Test(expected = InvalidParameterException::class)
    fun `constructor » latitude » overflow negative boundary`() {
        Point(-90.1, .0, .0)
    }

    @Test(expected = InvalidParameterException::class)
    fun `constructor » latitude » overflow positive boundary`() {
        Point(90.1, .0, .0)
    }

    /* ***** [ longitude ] ***** */

    @Test
    fun `constructor » longitude » negative boundary`() {
        val point = Point(.0, -180.0, .0)
        Assert.assertNotNull(point)
    }

    @Test(expected = InvalidParameterException::class)
    fun `constructor » longitude » overflow negative boundary`() {
        Point(.0, -180.1, .0)
    }

    @Test(expected = InvalidParameterException::class)
    fun `constructor » longitude » overflow positive boundary`() {
        Point(.0, 180.1, .0)
    }

    /* ***** [ getWS84Radius ] ***** */

    @Test
    fun `getWS84Radius » radius by latitude`() {
        val point = Point(latitude = 52.0)

        val actual = point.getWS84Radius()
        Assert.assertEquals(6364.9, actual, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `getWS84Radius » radius by latitude with elevation`() {
        val point = Point(latitude = 52.0, elevation = 1_000.0) // 1 km

        val actual = point.getWS84Radius()
        Assert.assertEquals(6365.9, actual, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `getWS84Radius » radius by latitude with negative elevation`() {
        val point = Point(latitude = 52.0, elevation = -1_000.0) // 1 km

        val actual = point.getWS84Radius()
        Assert.assertEquals(6363.9, actual, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    /* ***** [ translate ] ***** */

    @Test
    fun `translate » basic addition`() {
        // given
        val point = Point()
        val vector = Vector(northbound = 10.0)

        // action
        val actual = point.translate(vector)

        // assert
        Assert.assertEquals(10.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » basic subtraction`() {
        // given
        val point = Point()
        val vector = Vector(northbound = -10.0)

        // action
        val actual = point.translate(vector)

        // assert
        Assert.assertEquals(-10.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » basic addition maximum`() {
        // given
        val point = Point()
        val vector = Vector(northbound = 90.0)

        // action
        val actual = point.translate(vector)

        // assert
        Assert.assertEquals(90.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » basic addition minimum`() {
        // given
        val point = Point()
        val vector = Vector(northbound = -90.0)

        // action
        val actual = point.translate(vector)

        // assert
        Assert.assertEquals(-90.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » basic 2 addition minimum`() {
        // given
        val point = Point(latitude = 85.0)
        val vector = Vector(northbound = 5.0)

        // action
        val actual = point.translate(vector)

        // assert
        Assert.assertEquals(90.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » overflow » 2 addition minimum`() {
        // given
        val point = Point(latitude = 85.0)
        val vector = Vector(northbound = 6.0)

        // action
        val actual = point.translate(vector)

        // assert
        Assert.assertEquals(89.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    //    @Test
//    fun `translate » addition almost wrapped`(){
//        // given
//        val point = Point(52.37, 4.90, 5.0)
//        val vector = Vector(northbound = 38.0)
//
//        // action
//        val actual = point.translate(vector)
//
//        // assert
//        Assert.assertEquals(62.37, actual.latitude, 0.001)
//    }


//    @Test
//    fun `translate » addition wrapped`(){
//        // given
//        val point = Point(52.37, 4.90, 5.0)
//        val vector = Vector(northbound = 100.0)
//
//        // action
//        val actual = point.translate(vector)
//
//        // assert
//        Assert.assertEquals(62.37, actual.latitude, 0.001)
//    }


    /* ***** [ real-life examples ] ***** */

    @Test
    fun `translate » real life » north-west from novaya Zemlya`() {
        // given
        val novayaZemlya = Point(latitude = 76.60, longitude = 67.45)
        val northwest =
            Vector(northbound = 4.0, eastbound = -10.0) // notice negative eastbound value

        // action
        val actual = novayaZemlya.translate(northwest) // roughly Zemlya Georga

        // assert
        Assert.assertEquals(80.59, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(57.45, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » real life » east from greenland`() {
        // given
        val greenland = Point(latitude = 83.00, longitude = -36.5)
        val north = Vector(northbound = -4.0, eastbound = 55.0) // notice negative northbound value

        // action
        val actual = greenland.translate(north) // roughly Svalbard

        // assert
        Assert.assertEquals(79.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(18.5, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » real life » east from new Zealand`() {
        // given
        val newZealand = Point(latitude = -44.0, longitude = 170.0)
        val east = Vector(eastbound = 12.5)

        // action
        val actual = newZealand.translate(east) // roughly Chatham Island

        // assert
        Assert.assertEquals(-43.99, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(-177.5, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » real life » west from Bearing Strait`() {
        // given
        val bearingStrait = Point(latitude = 66.0, longitude = -168.0)
        val southWest = Vector(northbound = -0.25, eastbound = -11.25) // notice negative values

        // action
        val actual = bearingStrait.translate(southWest) // roughly Zaliv Kresta

        // assert
        Assert.assertEquals(65.75, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(-179.25, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » real life » west from Bearing Strait over the Antimeridian`() {
        // given
        val bearingStrait = Point(latitude = 66.0, longitude = -168.0)
        val southWest = Vector(northbound = -10.0, eastbound = -154.0) // notice negative values

        // action
        val actual = bearingStrait.translate(southWest) // roughly Zaliv Kresta

        // assert
        Assert.assertEquals(56.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(38.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    /* ***** */

    @Test
    fun `translate » round trip » east around the world`() {

        // For this example we're going to imagine an imaginary island just north-east of the null-island.
        // We're going to call this the new "nullIsland"

        // given
        val nullIsland = Point(latitude = 0.01, longitude = 0.01)
        val east = Vector(eastbound = 360.0 + 1.0) // around the world plus a little bit

        // action
        val actual = nullIsland.translate(east) // east from starting point

        // assert
        Assert.assertEquals(0.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(1.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » rount trip » west around the world`() {

        // For this example we're going to imagine an imaginary island just north-east of the null-island.
        // We're going to call this the new "nullIsland"

        // given
        val nullIsland = Point(latitude = 0.01, longitude = 0.01)
        val east = Vector(eastbound = -360.0 - 2.0) // around the world plus a little bit

        // action
        val actual = nullIsland.translate(east) // west from starting point

        // assert
        Assert.assertEquals(0.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(-2.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }


    @Test
    fun `translate » rount trip » north around the world`() {

        // For this example we're going to imagine an imaginary island just north-east of the null-island.
        // We're going to call this the new "nullIsland"

        // given
        val nullIsland = Point(latitude = 0.01, longitude = 0.01)
        val north = Vector(northbound = 360.0 + 2.0) // around the world plus a little bit

        // action
        val actual = nullIsland.translate(north) // north from starting point

        // assert
        Assert.assertEquals(2.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `translate » rount trip » south around the world`() {

        // For this example we're going to imagine an imaginary island just north-east of the null-island.
        // We're going to call this the new "nullIsland"

        // given
        val nullIsland = Point(latitude = 0.01, longitude = 0.01)
        val north = Vector(northbound = -360.0 - 2.0) // around the world plus a little bit

        // action
        val actual = nullIsland.translate(north) // north from starting point

        // assert
        Assert.assertEquals(-2.0, actual.latitude, ERROR_MARGIN_MEDIUM_DISTANCE)
        Assert.assertEquals(0.0, actual.longitude, ERROR_MARGIN_MEDIUM_DISTANCE)
    }


    /* ***** [ bearing ] ***** */

    @Test
    fun `bearing » simple numbers`() {
        val a = Point(2.0, 2.0)
        val b = Point(3.0, 3.0)

        // assert
        Assert.assertEquals(44.95, a.getBearing(b), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `bearing » los angeles to new york`() {
        val losAngeles = Point(34.122222, -118.4111111)
        val newYork = Point(40.66972222, -73.94388889)

        // assert
        Assert.assertEquals(66.06, losAngeles.getBearing(newYork), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `bearing » Kansas City to St Louis`() {
        val kansasCity = Point(39.099912, -94.581213)
        val stLouis = Point(38.627089, -90.200203)

        // assert
        Assert.assertEquals(96.51, kansasCity.getBearing(stLouis), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `bearing » Baghdad to Osaka`() {
        val baghdad = Point(35.0, 45.0)
        val osaka = Point(35.0, 135.0)

        // assert
        Assert.assertEquals(60.16, baghdad.getBearing(osaka), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

}