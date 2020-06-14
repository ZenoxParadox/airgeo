package com.airgeo.interfaces

import com.airgeo.Point
import com.airgeo.RANGE_LONGITUDE
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Tests for [Translatable]
 *
 * Created by Zenox on 9-6-2020 at 16:09.
 */
class TranslatableTest {

    private lateinit var translatable: Point

    @Before
    fun setup(){
        translatable = Point()
    }

    @Test
    fun `wrapping » longitude » zero`(){
        val unwrapped = 0.0
        val actual = translatable.wrapLongitude(unwrapped)

        Assert.assertEquals(0.0, actual, 0.0)
    }

    @Test
    fun `wrapping » longitude » 100`(){
        val actual = translatable.wrapLongitude(100.0)

        Assert.assertEquals(100.0, actual, 0.0)
    }

    @Test
    fun `wrapping » longitude » minimal`(){
        val unwrapped = RANGE_LONGITUDE.start
        val actual = translatable.wrapLongitude(unwrapped)

        Assert.assertEquals(RANGE_LONGITUDE.start, actual, 0.0)
    }

    @Test
    fun `wrapping » longitude » maximum`(){
        val unwrapped = RANGE_LONGITUDE.endInclusive
        val actual = translatable.wrapLongitude(unwrapped)

        Assert.assertEquals(RANGE_LONGITUDE.endInclusive, actual, 0.0)
    }

    /* ********** [ wrapping ] ********** */

    @Test
    fun `wrapping » latitude » simple`(){
        val unwrapped = 90.0 + 5.0
        val actual = translatable.wrapLatitude(unwrapped)

        Assert.assertEquals(85.0, actual, 0.0)
    }

    @Test
    fun `wrapping » longitude » wrapping around positive`(){
        val actual = translatable.wrapLongitude(185.0)

        Assert.assertEquals(-175.0, actual, 0.01)
    }

    @Test
    fun `wrapping » longitude » wrapping around positive 2`(){
        val actual = translatable.wrapLongitude(181.0)

        Assert.assertEquals(-179.0, actual, 0.01)
    }

    @Test
    fun `wrapping » longitude » wrapping 2 around positive 2`(){
        val value = 179.0 + 2
        val actual = translatable.wrapLongitude(value)

        Assert.assertEquals(-179.0, actual, 0.01)
    }

}