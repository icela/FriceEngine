package org.frice.anim.move


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

