@file:Suppress("NOTHING_TO_INLINE")

package org.frice.obj

import org.frice.utils.shape.FPoint
import org.frice.utils.shape.FShapeQuad

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.3
 */
interface AbstractObject {
	var x: Double
	var y: Double

	var rotate: Double

	fun move(x: Double, y: Double) {
		this.x += x
		this.y += y
	}
}

/**
 * Created by ice1000 on 2016/8/20.
 * @author ice1000
 * @since v0.4
 */
interface FContainer : FShapeQuad {

	override var x: Double
	override var y: Double

	fun containsPoint(px: Int, py: Int) = px >= x && px <= x + width && py >= y && py <= y + height

	operator fun contains(point: FPoint) = containsPoint(point.x, point.y)
}

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3
 */
interface Collidable {
	/**
	 * @since v1.6.7
	 * @see org.frice.utils.shape.FQuad
	 */
	val box: FShapeQuad

	fun collides(other: Collidable): Boolean = box.let { myBox ->
		val hisBox = other.box
		myBox.x + myBox.width >= hisBox.x && hisBox.y <= myBox.y + myBox.height &&
			myBox.x <= hisBox.x + hisBox.width &&
			myBox.y <= hisBox.y + hisBox.height
	}
}

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.4
 */
abstract class PhysicalObject(
	override var x: Double,
	override var y: Double) : AbstractObject, Collidable, FContainer {
	open var died = false
	var collisionBox: FShapeQuad? = null
	override val box: FShapeQuad get() = collisionBox ?: this
	override var rotate = 0.0
}
