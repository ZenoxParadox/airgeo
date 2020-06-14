package com.airgeo

import org.junit.Assert
import org.junit.Test

/**
 * Tests for [Rectangle]
 *
 * Created by Zenox on 12-6-2020 at 15:31.
 */
class RectangleTest {

    @Test(expected = IllegalArgumentException::class)
    fun `constructor » cannot initialize with equal points`() {
        val a = Point(1.0, 1.0)
        val b = Point(1.0, 1.0)

        Assert.assertNull(Rectangle(a, b))
    }

    @Test
    fun `constructor » should initialize with different points`() {
        val a = Point(1.0, 1.0)
        val b = Point(1.1, 1.0)

        val actual = Rectangle(a, b)
        Assert.assertNotNull(actual)
    }

//    @Test(expected = IllegalArgumentException::class)
//    fun `contains » not allow size to exceed max allowed`() {
//        val a = Point(-90.0, 179.0)
//        val b = Point(90.0, 0.0)
//
//        Rectangle(a, b)
//
//        // Notice: no assert!
//    }

    /* ********** [ translate ] ********** */

    @Test
    fun `contains » simple numbers no overlap`() {
        val a = Point(1.0, 1.0)
        val b = Point(5.0, 5.0)
        val inside = Point(2.5, 2.5)
        val outside = Point(10.0, 10.0)

        val rectangle = Rectangle(a, b)

        Assert.assertFalse(rectangle.contains(outside))
        Assert.assertTrue(rectangle.contains(inside))
    }

    @Test
    fun `contains » point should be contained in big area`() {
        val southwest = Point(5.0, 135.0)
        val northeast = Point(35.0, 175.0)

        val inside = Point(25.0, 150.0)
        val outside = Point(25.0, 100.0)

        val rectangle = Rectangle(southwest, northeast)

        Assert.assertTrue(rectangle.contains(inside))
        Assert.assertFalse(rectangle.contains(outside))
    }

    @Test
    fun `contains » point should not be contained in big area on other side of anti maridian`() {
        val southwest = Point(5.0, 135.0)
        val northeast = Point(35.0, 175.0)
        val query = Point(25.0, -175.0)

        val rectangle = Rectangle(southwest, northeast)

        Assert.assertFalse(rectangle.contains(query))
    }

    /* ********* [contains ] ********** */

    @Test
    fun `contains » rectangle is spanning over anti maridian`() {
        val southwest = Point(-10.0, 175.0)
        val northeast = Point(-5.0, -175.0)

        val queryPositive = Point(-7.0, 177.0)
        val queryNegative = Point(-7.0, -177.0)
        val queryOutside = Point(-10.1, -179.0) // just over southern border

        val rectangle = Rectangle(southwest, northeast)

        Assert.assertTrue(rectangle.contains(queryPositive))
        Assert.assertTrue(rectangle.contains(queryNegative))
        Assert.assertFalse(rectangle.contains(queryOutside))
    }

    @Test
    fun `contains » area spanning over pole`() {
        val southwest = Point(80.0, 45.0)
        val northeast = Point(85.0, -125.0)
        val rectangle = Rectangle(southwest, northeast)

        val outside = Point(82.0, -110.0) // just outside
        val inside = Point(85.0, -150.0)

        Assert.assertFalse(rectangle.contains(outside))
        Assert.assertTrue(rectangle.contains(inside))
    }

    @Test
    fun `contains » area spanning over anti maridian and equator`() {
        val a = Point(-1.0, 177.0)
        val b = Point(2.0, -178.0)
        val rectangle = Rectangle(a, b)

        val outside = Point(1.0, -177.0)
        val outside2 = Point(1.0, -177.0)
        val outside3 = Point(0.0, 0.0)
        val inside = Point(0.0, 179.0)
        val inside2 = Point(0.0, -179.0)

        Assert.assertFalse(rectangle.contains(outside))
        Assert.assertFalse(rectangle.contains(outside2))
        Assert.assertFalse(rectangle.contains(outside3))

        Assert.assertTrue(rectangle.contains(inside))
        Assert.assertTrue(rectangle.contains(inside2))
    }

}