package org.frice.util.shape

import java.awt.geom.Rectangle2D

interface FShapeInt {
	var width: Int
	var height: Int
}

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
open class FCircle(r: Double) : FOval(r, r)

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 * @param rh horizontal radius
 * @param rv vertical radius
 */
open class FOval(rh: Double, rv: Double) : FShapeInt {
	override var width = rh.toInt() shl 1
	override var height = rv.toInt() shl 1
}


/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3
 */
data class FPoint(var x: Int, var y: Int)

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
open class FRectangle(override var width: Int, override var height: Int) : FShapeInt {
	constructor(rect: Rectangle2D) : this(rect.width.toInt(), rect.height.toInt())
	constructor(width: Double, height: Double) : this(width.toInt(), height.toInt())
	constructor(width: Float, height: Float) : this(width.toInt(), height.toInt())
}

