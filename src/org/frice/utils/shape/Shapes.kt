package org.frice.utils.shape

import java.awt.geom.Rectangle2D

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
interface FShape

interface FShapeInt : FShape {
	var width: Int
	var height: Int
}

interface FShapeDouble : FShape {
	var width: Double
	var height: Double
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
 */
open class FOval(rh: Double, rv: Double) : FShapeInt {
	override var width = (rh + rh).toInt()
	override var height = (rv + rv).toInt()
}


/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3
 */
data class FPoint(var x: Int, var y: Int) : FShape

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

