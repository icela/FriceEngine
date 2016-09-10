package org.frice.game.utils.graphics.shape

import java.awt.geom.Rectangle2D

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
interface FShape {
	var width: Int
	var height: Int
}


/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class FCircle(r: Double) : FOval(r, r)


/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
open class FOval(var rh: Double, var rv: Double) : FShape {
	override var width = (rh * 2).toInt()
	override var height = (rv * 2).toInt()
}


/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3
 */
class FPoint(var x: Int, var y: Int) : FShape {
	override var width = 1
		set (value) = Unit
	override var height = 1
		set (value) = Unit
}


/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class FRectangle(override var width: Int, override var height: Int) : FShape {
	constructor(rect: Rectangle2D) : this(rect.width.toInt(), rect.height.toInt())

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is FRectangle) return false
		if (height != other.height || width != other.width) return false
		return true
	}

	override fun hashCode(): Int {
		var result = width
		result = 31 * result + height
		return result
	}

	//	infix fun rectCollideRect(o: FRectangle) = (x > o.x && )
}