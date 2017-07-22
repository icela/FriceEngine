package org.frice.game.utils.graphics.shape

import java.awt.geom.Rectangle2D
import java.util.*
import java.lang.Math.*

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
}

class FQuad(var x: Double, var y: Double, override var width: Double, override var height: Double) : FShapeDouble

/**
 * 通过两点构造一条直线
 * copied from https://github.com/ice1000/FindLine/blob/master/src/ice1000/models/Line.kt
 * my another repo
 * Created by ice1000 on 2016/8/8.
 *
 * @author ice1000
 */
open class FLine(one: FPoint, two: FPoint) {

	private val a = two.y - one.y
	private val b = one.x - two.x
	private val c = two.x * one.y - one.x * two.y
	val set = HashSet<FPoint>()

	init {
		if (a != 0 || b != 0) {
			(min(one.x, two.x)..max(one.x, two.x))
					.forEach { x -> set.add(FPoint(x, x2y(x))) }
			(min(one.y, two.y)..max(one.y, two.y))
					.forEach { y -> set.add(FPoint(y2x(y), y)) }
		}
	}

	fun x2y(x: Int) = if (b == 0) c / a else -(a * x + c) / b
	fun y2x(y: Int) = if (a == 0) c / b else -(b * y + c) / a

	override operator fun equals(other: Any?): Boolean {
		if (other == null || other !is FLine) return false
		return a / other.a == b / other.b && b / other.b == c / other.c
	}

	override fun hashCode(): Int {
		var result = a.hashCode()
		result = 31 * result + b.hashCode()
		result = 31 * result + c.hashCode()
		return result
	}
}
