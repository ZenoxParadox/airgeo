package com.airgeo

import com.airgeo.interfaces.Translatable

// TODO Rectangles only support a span of < 180 degrees (longitude and latitude)
private const val MAX_SPAN = 180.0

/**
 * Represents a rectangular area, defined by 2 corner [Point] instances.
 */
class Rectangle(private val southwest: Point, private val northeast: Point) : Translatable<Rectangle> {

    init {
        if (southwest == northeast) {
            throw IllegalArgumentException("Invalid corner points for rectangle.")
        }

        // TODO Rectangles only support a span of < 180 degrees (longitude and latitude)
        if (true == false) {
            throw IllegalArgumentException("Rectangle latitude span exceeds the maximum.")
        }

        if (true == false) {
            throw IllegalArgumentException("Rectangle longitude span exceeds the maximum.")
        }
    }

    fun contains(point: Point): Boolean {
        val bl = southwest
        val tr = northeast

        val isLongInRange = if (tr.longitude < bl.longitude) {
            point.longitude >= bl.longitude || point.longitude <= tr.longitude
        } else {
            point.longitude >= bl.longitude && point.longitude <= tr.longitude
        }

        return point.latitude >= bl.latitude  &&  point.latitude <= tr.latitude  && isLongInRange
    }

    override fun translate(vector: Vector): Rectangle {
        val imageNortheast = northeast.translate(vector)
        val imageSouthwest = southwest.translate(vector)

        return Rectangle(imageSouthwest, imageNortheast)
    }

}