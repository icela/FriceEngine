package org.frice.obj

import org.frice.anim.FAnim
import org.frice.anim.RotateAnim
import org.frice.anim.move.*
import org.frice.anim.scale.ScaleAnim
import org.frice.platform.FriceImage
import org.frice.resource.FResource
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

	abstract val resource: FResource

	infix fun scale(p: DoublePair) = scale(p.x, p.y)

	abstract fun scale(x: Double, y: Double)

	open fun move(p: DoublePair) = move(p.x, p.y)

	open fun move(x: Double, y: Double) {
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
				is MoveAnim -> move(a.delta)
				is ScaleAnim -> scale(a.after)
				is RotateAnim -> rotate(a.rotate)
			}
		}
	}

	fun addAnim(anim: FAnim) = anims.add(anim)

	fun stopAnims() = anims.clear()

	interface ImageOwner {
		val image: FriceImage
	}
}