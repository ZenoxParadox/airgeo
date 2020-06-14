package com.airgeo

/**
 * Represents a transxxx (as in xxx) for other 3D elements (such as [Point], [Line], [PolyLine],
 * and [Rectangle].
 *
 * Constructed from two corner points ([northbound] and [eastbound]) and the difference of the
 * change (translation) in meters.
 */
data class Vector(val northbound: Double = 0.0, val eastbound: Double = 0.0, val distance: Double = 0.0)