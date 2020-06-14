package com.airgeo

import com.airgeo.interfaces.Translatable
import com.airgeo.interfaces.Traversable
import com.airgeo.method.Euclidean
import com.airgeo.method.MeasureMethod

/**
 * Represents a line between two [Point] instances.
 */
class Line(val begin: Point, val end: Point) : Traversable, Translatable<Line> {

    init {
        // TODO Lines only need to support a span of less than 180 degrees longitude and latitude
        if(true == false){
            throw IllegalArgumentException("Line span exceeds the maximum!")
        }
    }

    override fun getLength(method: MeasureMethod): Double {
        return method.measure(begin, end)
    }

    override fun getPoint(distance: Double, method: MeasureMethod): Point {
        return method.interpolate(begin, end, distance)
    }

    override fun translate(vector: Vector): Line {
        val imageBegin = begin.translate(vector)
        val imageEnd = end.translate(vector)

        return Line(imageBegin, imageEnd)
    }

    override fun toString(): String {
        val euclideanLength = getLength(Euclidean())
        return "[${begin.latitude}:${begin.longitude}] - [${end.latitude}:${end.longitude}] (euclidean length: $euclideanLength)"
    }

}