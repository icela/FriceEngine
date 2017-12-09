package org.frice.anim.move

import org.frice.anim.move.DoublePair.Factory.fromK
import org.frice.anim.move.DoublePair.Factory.fromM
import org.frice.obj.AbstractObject

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

	override val delta: DoublePair
		get() {
			mx = (now - start) * ax / 2
			my = (now - start) * ay / 2
			return fromM((now - lastRefresh) * mx, (now - lastRefresh) * my)
		}

	companion object Factory {
		/**
		 * @author ice1000
		 * @since v1.7.6
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
	override val delta: DoublePair
		get() {
			val a = targetObj.x - self.x
			val b = targetObj.y - self.y
			val c = Math.sqrt(a * a + b * b)
			val deltaTime = now - lastRefresh
			val pair = fromK(deltaTime * (speed * a / c), deltaTime * (speed * b / c))
			lastRefresh = now
			return pair
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
	override val delta: DoublePair
		get() {
			val a = targetObj.x - self.x
			val b = targetObj.y - self.y
			@Suppress("LocalVariableName")
			val `deltaTime*proportion` = (now - lastRefresh) * proportion
			val pair = fromK(`deltaTime*proportion` * (a), `deltaTime*proportion` * (b))
			lastRefresh = now
			return pair
		}
}

