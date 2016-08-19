package org.frice.game.obj

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.4
 */
interface PhysicalObject : AbstractObject {
	val width: Double
	val height: Double

	var died: Boolean

	fun containsPoint(px: Int, py: Int) = px >= x && px <= x + width && py >= y && py <= y + height
}