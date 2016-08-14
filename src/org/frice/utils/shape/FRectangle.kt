package org.frice.utils.shape

import java.awt.geom.Rectangle2D

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class FRectangle(override var x: Int, override var y: Int, val width: Int, val height: Int) : FShape {
	constructor(width: Int, height: Int) : this(0, 0, width, height)

	constructor(rect: Rectangle2D) : this(rect.x.toInt(), rect.y.toInt(), rect.width.toInt(), rect.height.toInt())

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is FRectangle) return false
		if (height != other.height || width != other.width || x != other.x || y != other.y) return false
		return true
	}

	override fun hashCode(): Int {
		var result = x
		result = 31 * result + y
		result = 31 * result + width
		result = 31 * result + height
		return result
	}

//	infix fun collide(o: FRectangle) = (x > o.x && )


}