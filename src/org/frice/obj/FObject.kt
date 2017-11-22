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
	open var id = -1
	val anims = LinkedList<FAnim>()
	val targets = LinkedList<Pair<PhysicalObject, SideEffect>>()
	override var rotate = 0.0

	/**
	 * physics force
	 * will change with mass(see #runAnims below)
	 */
	private val force = AccelerateMove(0.0, 0.0)

	abstract val collideBox: FShape

	abstract fun getResource(): FResource

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

	/**
	 * Magic! Don't touch!
	 */
	protected infix fun PhysicalObject.rectCollideRect(rect: PhysicalObject) =
			x + width >= rect.x && rect.y <= y + height &&
					x <= rect.x + rect.width &&
					y <= rect.y + rect.height

	//	protected infix fun PhysicalObject.rectCollideOval(oval: PhysicalObject): Boolean {
	//		if (!rectCollideRect(oval)) return false
	//		val xxx = if (x + width / 2 > oval.x + oval.width / 2) x else x + width
	//		val yyy = if (y + height / 2 > oval.y + oval.height / 2) y else y + height
	//	}

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

	internal fun checkCollision() {
		targets.removeAll { (first) -> first.died }
		targets.forEach { (first, second) -> if (isCollide(first)) second() }
	}

	fun addAnim(anim: FAnim) = anims.add(anim)
	fun addCollider(o: PhysicalObject, e: SideEffect) = addCollider(o to e)
	fun addCollider(p: Pair<PhysicalObject, SideEffect>) = targets.add(p)
	fun addCollider(o: PhysicalObject, e: () -> Unit) = addCollider(o to SideEffect(e))
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