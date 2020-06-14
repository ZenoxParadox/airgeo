package com.airgeo.interfaces

import com.airgeo.Point
import com.airgeo.method.MeasureMethod

/**
 * Contract for classes that you can travel between and thus need to expose a certain length.
 * The length between two points can be measured in varying [MeasureMethod] and may differ in
 * accuracy and or performance.
 */
interface Traversable {

    fun getLength(method: MeasureMethod): Double

    fun getPoint(distance: Double, method: MeasureMethod): Point

}