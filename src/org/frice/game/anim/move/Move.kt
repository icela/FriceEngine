package org.frice.game.anim.move

import org.frice.game.anim.FAnim

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 */
abstract class MoveAnim() : FAnim() {
	abstract val delta: DoublePair

	protected var cache: Double = start
}


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
			val pair = DoublePair((now - cache) * x, (now - cache) * y)
			cache = now
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
			val pair = DoublePair((now - cache) * x, (now - cache) * y)
			cache = now
			return pair
		}
}

data class DoublePair(var x: Double, var y: Double) {

	operator fun times(double: Double) = DoublePair(x / double, y / double)
}


/**
 * Move with force (accelerate), give accelerate value to ax and by
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 * @param ax accelerate on x
 * @param ay accelerate on y
 */

class AccelerateMove(var ax: Double, var ay: Double) : SimpleMove(0, 0) {
	companion object {
		@JvmStatic fun getGravity() = AccelerateMove(0.0, 10.0)
		@JvmStatic fun getGravity(g: Double) = AccelerateMove(0.0, g)
	}

	private var mx = 0.0
	private var my = 0.0

	override val delta: DoublePair
		get() {
			mx = (now - start) * ax
			my = (now - start) * ay
			return DoublePair((now - cache) / 1000 * mx, (now - cache) / 1000 * my)
		}

}


/**
 * Define your own move object as you like.
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
abstract class CustomMove() : MoveAnim() {
	private val timeFromStart: Double
		get() = System.currentTimeMillis() - start

	abstract fun getXDelta(timeFromBegin: Double): Double
	abstract fun getYDelta(timeFromBegin: Double): Double

	override val delta: DoublePair
		get() = DoublePair(getXDelta(timeFromStart), getYDelta(timeFromStart))

}