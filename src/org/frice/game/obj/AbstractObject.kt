package org.frice.game.obj

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.3
 */
interface AbstractObject {
	var x: Double
	var y: Double
	val width: Double
	val height: Double

	fun containsPoint(px: Int, py: Int) = px >= x && px <= x + width && py >= y && py <= y + height
}