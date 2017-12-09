package org.frice.anim.move

import org.frice.obj.AbstractObject


/**
 * Simple move anim
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 * @param x pixels per second
 * @param y pixels per second
 */
open class SimpleMove(var x: Int, var y: Int) : MoveAnim() {

	override val delta: DoublePair
		get() {
			val deltaTime = now - lastRefresh
			val pair = DoublePair.fromK(deltaTime * x, deltaTime * y)
			lastRefresh = now
			return pair
		}

	companion object Factory {
		/**
		 * @author ice1000
		 * @since v1.7.6
		 * @param angle degree of counterclockwise rotation from the x-axis
		 * @param speed pixels per second
		 */
		@JvmStatic
		@Deprecated("It's recommended to use AccurateMove.",
			level = DeprecationLevel.WARNING,
			replaceWith = ReplaceWith("AccurateMove.byAngle", "org.frice.anim.move"))
		fun byAngle(angle: Double, speed: Int) =
			SimpleMove(Math.sin(angle).toInt() * speed, Math.cos(angle).toInt() * speed)
	}
}

/**
 * Accurate Move anim, reduces automatic conversions.
 * Deltas are d1, the speed will be more specific.
 *
 * @author ice1000
 * @since v0.5.1
 * @param x pixels per second
 * @param y pixels per second
 */
open class AccurateMove(var x: Double, var y: Double) : MoveAnim() {

	override val delta: DoublePair
		get() {
			val deltaTime = now - lastRefresh
			val pair = DoublePair.fromK(deltaTime * x, deltaTime * y)
			lastRefresh = now
			return pair
		}

	companion object Factory {
		/**
		 * @author ice1000
		 * @since v1.7.6
		 * @param angle degree of counterclockwise rotation from the x-axis
		 * @param speed pixels per second
		 */
		@JvmStatic
		fun byAngle(angle: Double, speed: Double) =
			AccurateMove(Math.sin(angle) * speed, Math.cos(angle) * speed)
	}
}

/**
 * @author ice1000
 * @since v1.7.2
 * @param self the moving object itself
 * @param targetX the x axis of the target location
 * @param targetY the y axis of the target location
 * @param speed pixels per second
 */
open class DirectedMove(self: AbstractObject, targetX: Double, targetY: Double, speed: Double) : SelfCenteredMoveAnim(self) {
	val x: Double
	val y: Double

	init {
		val a = targetX - self.x
		val b = targetY - self.y
		val c = Math.sqrt(a * a + b * b)
		x = speed * a / c
		y = speed * b / c
	}

	override val delta: DoublePair
		get() {
			val deltaTime = now - lastRefresh
			val pair = DoublePair.fromK(deltaTime * x, deltaTime * y)
			lastRefresh = now
			return pair
		}
}
