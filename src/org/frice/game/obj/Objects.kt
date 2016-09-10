package org.frice.game.obj

import org.frice.game.anim.FAnim
import org.frice.game.anim.RotateAnim
import org.frice.game.anim.move.AccelerateMove
import org.frice.game.anim.move.MoveAnim
import org.frice.game.anim.scale.ScaleAnim
import org.frice.game.resource.FResource
import org.frice.game.utils.graphics.shape.FPoint
import org.frice.game.utils.graphics.shape.FShape
import org.frice.game.utils.misc.times
import java.awt.image.BufferedImage
import java.util.*

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

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.4
 */
abstract class PhysicalObject : AbstractObject, CollideBox, FContainer {
	open var died = false
	override var rotate = 0.0
	var mass = 1.0
		set(value) {
			if (value <= 0) field = 0.00001
			else field = value
		}
}


/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class FObject : PhysicalObject() {
	open var id = -1

	val anims = LinkedList<FAnim>()

	val targets = LinkedList<Pair<PhysicalObject, OnCollideEvent>>()

	val gravityCentre = LinkedList<AbstractObject>()

	override var rotate = 0.0

	/**
	 * physics force
	 * will change with mass(see #runAnims below)
	 */
	val force = AccelerateMove(0.0, 0.0)

	abstract val collideBox: FShape

	abstract fun getResource(): FResource

	abstract infix fun scale(p: Pair<Double, Double>)

	open infix fun move(p: Pair<Double, Double>) {
		x += p.first
		y += p.second
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

	fun runAnims() = anims.forEach { a ->
		// move force first
		move(force.getDelta().times(mass))
		when (a) {
			is MoveAnim -> move(a.getDelta())
			is ScaleAnim -> scale(a.getAfter())
			is RotateAnim -> rotate(a.getRotate())
		}
	}

	fun checkCollision() {
		targets.removeAll { t -> t.first.died }
		targets.forEach { t -> if (isCollide(t.first)) t.second.handle() }
	}

	/**
	 * add a force to this object
	 * the effect of the force have sth to do with the mass
	 */
	fun addForce(x: Double, y: Double) {
		force.ax += x
		force.ay += y
	}

	/**
	 * Created by ice1000 on 2016/8/16.
	 * @author ice1000
	 * @since v0.3
	 */
	interface OnCollideEvent {
		fun handle()
	}

	interface ImageOwner {
		val image: BufferedImage
	}
}

