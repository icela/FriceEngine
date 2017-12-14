package org.frice.anim.move

import org.frice.obj.AbstractObject
import org.frice.obj.FObject

/**
 * Move with acceleration, give acceleration to x and y
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
	override fun `{-# do #-}`(obj: FObject) {
		mx = (now - start) * ax / 2
		my = (now - start) * ay / 2
		val deltaTime = (now - lastRefresh) / 1e6
		lastRefresh = now
		obj.move(deltaTime * mx, deltaTime * my)
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
	override fun `{-# do #-}`(obj: FObject) {
		val a = targetObj.x - self.x
		val b = targetObj.y - self.y
		val c = Math.sqrt(a * a + b * b)
		@Suppress("LocalVariableName")
		val `deltaTime*speed{div}c` = (now - lastRefresh) * speed / c / 1e3
		lastRefresh = now
		obj.move(`deltaTime*speed{div}c` * a, `deltaTime*speed{div}c` * b)
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
	override fun `{-# do #-}`(obj: FObject) {
		val a = targetObj.x - self.x
		val b = targetObj.y - self.y
		@Suppress("LocalVariableName")
		val `deltaTime*proportion` = (now - lastRefresh) * proportion / 1e3
		lastRefresh = now
		obj.move(`deltaTime*proportion` * a, `deltaTime*proportion` * b)
	}
}

