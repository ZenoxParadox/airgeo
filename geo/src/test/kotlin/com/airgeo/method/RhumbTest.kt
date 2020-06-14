package com.airgeo.method

import com.airgeo.ERROR_MARGIN_MEDIUM_DISTANCE
import com.airgeo.Point
import org.junit.Assert
import org.junit.Test

/**
 * Tests for [Rhumb]
 *
 * Created by Zenox on 13-6-2020 at 19:11.
 */
@Deprecated(message = "Since the method is not really for the test, the test for it has also been deprecated.")
class RhumbTest {

    private val method = Rhumb()

    @Test
    fun `measure » simple numbers`() {
        val a = Point(1.0, 1.0)
        val b = Point(2.0, 2.0)

        Assert.assertEquals(157.24, method.measure(a, b), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    @Test
    fun `measure » dover to calais`() {
        val dover = Point(51.127, 1.338)
        val calais = Point(50.964, 1.853)

        Assert.assertEquals(40.25, method.measure(dover, calais), ERROR_MARGIN_MEDIUM_DISTANCE)
    }

    /* ********** [interpolate] ********** */

    @Test(expected = NotImplementedError::class)
    fun `interpolate » dover to calais`() {
        val dover = Point(51.127, 1.338)
        val calais = Point(50.964, 1.853)

        method.interpolate(dover, calais, 20.0)

        // notice: no assert!
    }

}