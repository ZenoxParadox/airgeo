package com.airgeo

import org.junit.Assert
import org.junit.Test

const val ERROR_MARGIN_LONG_DISTANCE: Double = 0.1
const val ERROR_MARGIN_MEDIUM_DISTANCE: Double = 0.01
const val ERROR_MARGIN_SHORT_DISTANCE: Double = 0.001

class TestTest {

    @Test()
    fun `bla`() {

        val original = 80.0

        val one = original * (Math.PI / 180)
        val other = Math.toRadians(original)

        Assert.assertEquals(one, other, 0.00)
    }


}
