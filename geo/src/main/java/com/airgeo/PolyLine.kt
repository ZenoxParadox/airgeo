package com.airgeo

import com.airgeo.interfaces.Translatable
import com.airgeo.interfaces.Traversable
import com.airgeo.method.MeasureMethod
import java.util.*
import kotlin.math.abs

/**
 * Represents a line between two or more [Point] instances. Two points (minimum) are essential in
 * order to construct an instance.
 */
class PolyLine(private val points: List<Point>) : Traversable, Translatable<PolyLine> {

    init {
        if (points.size < 2) {
            throw IllegalArgumentException("Polyline requires 2 or more items.")
        }
    }

    /**
     * Returns a [Point] in the [Line] instances that make out this instance from the starting
     * point to the [distance] (in km).
     */
    override fun getPoint(distance: Double, method: MeasureMethod): Point {

        // variable to navigate to the correct line-segment
        var toTraverse = abs(distance)
        val totalLength = getLength(method)
        val lines = getLines()

        /*
        When distance is longer then the total length, we're just going to return the endpoint.
        Optionally, we could throw an exception.
        */
        if (toTraverse < totalLength) {
            for (line in lines) {
                val length = line.getLength(method)

                // navigate to the correct line-segment
                if (toTraverse > length) {
                    toTraverse -= length
                    continue
                }

                // SUGGESTION toTraverse could become a fraction rather then distance here?
                return method.interpolate(line.begin, line.end, toTraverse)
            }
        }

        // As an ultimate backup return the end of the line
        return lines.last().end
    }

    /**
     * Gives all the [Line] instances that make out this [PolyLine].
     */
    fun getLines(): LinkedList<Line> {
        val list = LinkedList<Line>()

        for (i in 0..points.size - 2) {
            if (i == points.size) {
                continue
            }

            val from = points[i]
            val to = points[i + 1]

            val line = Line(from, to)
            list.add(line)
        }

        return list
    }

    /**
     * Gets the sum of all line lengths in KM
     */
    override fun getLength(method: MeasureMethod): Double {
        val lines = getLines()
        var total = 0.0
        for (line in lines) {
            total += line.getLength(method)
        }

        return total
    }

    override fun translate(vector: Vector): PolyLine {
        val imagePoints = mutableListOf<Point>()

        for (point in points) {
            val imagePoint = point.translate(vector)
            imagePoints.add(imagePoint)
        }

        return PolyLine(imagePoints)
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendln("-".repeat(10))

        val lines = getLines()
        builder.appendln("points: ${points.size} (lines: ${lines.size})")
        for (line in lines) {
            builder.appendln("line: $line")
        }

        return builder.toString()
    }

}