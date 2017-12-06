package org.frice.anim.move

import org.frice.obj.FObject


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
			val pair = DoublePair.from1000(deltaTime * x, deltaTime * y)
			lastRefresh = now
			return pair
		}

}

/**
 * Accurate Move anim. Deltas are d1, the speed will be more spcific.
 *
 * @author ice1000
 * @since v0.5.1
 */
open class AccurateMove(var x: Double, var y: Double) : MoveAnim() {

	override val delta: DoublePair
		get() {
			val deltaTime = now - lastRefresh
			val pair = DoublePair.from1000(deltaTime * x, deltaTime * y)
			lastRefresh = now
			return pair
		}
}

/**
 * @param self the moving object itself
 * @param targetX the x axis of the target location
 * @param targetY the y axis of the target location
 * @author ice1000
 * @since v1.7.2
 */
open class DirectedMove(self: FObject, targetX: Double, targetY: Double, speed: Double) : SelfCenteredMoveAnim(self) {
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
			val pair = DoublePair.from1000(deltaTime * x, deltaTime * y)
			lastRefresh = now
			return pair
		}
}
