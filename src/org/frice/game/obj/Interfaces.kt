package org.frice.game.obj

import org.frice.game.utils.graphics.shape.FPoint

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.3
 */
interface  AbstractObject {
	var x: Double
	var y: Double

	var rotate: Double
}

/**
 * Created by ice1000 on 2016/8/20.
 * @author ice1000
 * @since v0.4
 */
interface FContainer {

	var x: Double
	var y: Double

	val width: Double
	val height: Double


	fun containsPoint(px: Int, py: Int) = px >= x && px <= x + width && py >= y && py <= y + height

	infix fun containsPoint(point: FPoint) = containsPoint(point.x, point.y)
}

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3
 */
interface CollideBox {
	fun isCollide(other: CollideBox): Boolean
}