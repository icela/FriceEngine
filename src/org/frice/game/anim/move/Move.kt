@file:Suppress("NOTHING_TO_INLINE")

package org.frice.game.anim.move

import org.frice.game.anim.FAnim
import org.frice.game.utils.time.Clock

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 */
abstract class MoveAnim : FAnim() {
	abstract val delta: DoublePair
	protected var lastRefresh: Double = start
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
// @Deprecated("AccurateMove is suggested.", ReplaceWith("AccurateMove()"), DeprecationLevel.WARNING)
open class SimpleMove(var x: Int, var y: Int) : MoveAnim() {

	override val delta: DoublePair get() {
		val pair = DoublePair.from1000((now - lastRefresh) * x, (now - lastRefresh) * y)
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

	override val delta: DoublePair get() {
		val pair = DoublePair.from1000((now - lastRefresh) * x, (now - lastRefresh) * y)
		lastRefresh = now
		return pair
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
		get() = Clock.current - start

	abstract fun getXDelta(timeFromBegin: Double): Double
	abstract fun getYDelta(timeFromBegin: Double): Double
	open fun update(timeFromBegin: Double) = Unit

	override val delta: DoublePair
		get() {
			update(timeFromStart)
			return DoublePair(getXDelta(timeFromStart), getYDelta(timeFromStart))
		}

}