package org.frice.obj

import org.frice.anim.FAnim
import org.frice.anim.RotateAnim
import org.frice.anim.move.*
import org.frice.anim.scale.ScaleAnim
import org.frice.platform.FriceImage
import org.frice.resource.FResource
import org.frice.utils.shape.FPoint
import org.frice.utils.shape.FShape
import java.util.*

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class FObject : PhysicalObject() {
	var id = -1
	val anims = LinkedList<FAnim>()
	override var rotate = 0.0

	/**
	 * physics force
	 * will change with mass(see #runAnims below)
	 */
	private val force = AccelerateMove(0.0, 0.0)

	abstract val resource: FResource

	infix fun scale(p: DoublePair) = scale(p.x, p.y)

	abstract fun scale(x: Double, y: Double)

	open infix fun move(p: DoublePair) = move(p.x, p.y)

	fun move(x: Double, y: Double) {
		this.x += x
		this.y += y
	}

	open infix fun rotate(angle: Double) {
		rotate += angle
	}

	private fun squaredDelta(d1: Double, d2: Double) = (d1 - d2) * Math.abs(d1 - d2)
	private fun targetMass(c: AbstractObject) = (c as? PhysicalObject)?.mass ?: 1.0

	internal fun runAnims() {
		anims.forEach { a ->
			when (a) {
				is MoveAnim -> this move a.delta
				is ScaleAnim -> this scale a.after
				is RotateAnim -> this rotate a.rotate
			}
		}
		// TODO bug
		//		if (gravityConstant != 0.0) gravityCentre.forEach { c ->
		//			unless (Math.abs(c.x - x) + Math.abs(c.y - y) < 1.5) {
		//				gravity.x += targetMass(c) * gravityConstant / squaredDelta(c.x, x)
		//				gravity.y += targetMass(c) * gravityConstant / squaredDelta(c.y, y)
		//			}
		//		}
		// move force
		move(force.delta / mass)
		// affected by gravity
		//		move(gravity)
	}

	fun addAnim(anim: FAnim) = anims.add(anim)
	fun stopAnims() = anims.clear()

	/**
	 * add a force to this object
	 * the effect of the force have sth to do with the mass
	 */
	fun addForce(x: Double, y: Double) {
		force.ax += x
		force.ay += y
	}

	infix fun addForce(p: FPoint) = addForce(p.x.toDouble(), p.y.toDouble())

	interface ImageOwner {
		val image: FriceImage
	}
}