package org.frice.game.obj

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.4
 */
abstract class PhysicalObject : AbstractObject() {
	abstract val width: Double
	abstract val height: Double

	open var died = false

	open fun containsPoint(px: Int, py: Int) = px >= x && px <= x + width && py >= y && py <= y + height
}