package org.frice.anim.move

import org.frice.obj.AbstractObject
import org.frice.obj.FObject

/**
 * Move with force (accelerate), give accelerate value to ax and by
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 * @param ax pixels per second squared
 * @param ay pixels per second squared
 */
class AccelerateMove(var ax: Double, var ay: Double) : SimpleMove(0, 0) {
	private var mx = 0.0
	private var my = 0.0
	override fun `do`(obj: FObject) {
		mx = (now - start) * ax / 2
		my = (now - start) * ay / 2
		obj.move((now - lastRefresh) * mx / 1e6, (now - lastRefresh) * my / 1e6)
	}

	companion object Factory {
		/**
		 * @author ice1000
		 * @since v1.7.7
		 * @param angle degree of counterclockwise rotation from the x-axis
		 * @param acceleration pixels per second squared
		 */
		@JvmStatic
		fun byAngle(angle: Double, acceleration: Double) =
			AccelerateMove(Math.sin(angle) * acceleration, Math.cos(angle) * acceleration)
	}
}

/**
 * Chase after the target, same speed, different direction
 * @author ice1000
 * @since v1.7.3
 * @param self the chasing object
 * @param targetObj the chased target
 * @param speed pixels per second
 */
class ChasingMove(self: AbstractObject, var targetObj: AbstractObject, var speed: Double) : SelfCenteredMoveAnim(self) {
	override fun `do`(obj: FObject) {
		val a = targetObj.x - self.x
		val b = targetObj.y - self.y
		val c = Math.sqrt(a * a + b * b)
		val deltaTime = now - lastRefresh
		lastRefresh = now
		obj.move(deltaTime * (speed * a / c) / 1e3, deltaTime * (speed * b / c) / 1e3)
	}
}

/**
 * Approaching the target, each second
 *
 * @author ice1000
 * @since v1.7.3
 * @param self the approaching object
 * @param targetObj the approached target
 * @param proportion per second
 */
class ApproachingMove(self: AbstractObject, var targetObj: AbstractObject, var proportion: Double) : SelfCenteredMoveAnim(self) {
	override fun `do`(obj: FObject) {
		val a = targetObj.x - self.x
		val b = targetObj.y - self.y
		@Suppress("LocalVariableName")
		val `deltaTime*proportion` = (now - lastRefresh) * proportion
		lastRefresh = now
		obj.move(`deltaTime*proportion` * a / 1e3, `deltaTime*proportion` * b / 1e3)
	}
}

