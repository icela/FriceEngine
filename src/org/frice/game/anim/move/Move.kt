package org.frice.game.anim.move

import org.frice.game.anim.FAnim
import org.frice.game.utils.time.Clock

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 */
abstract class MoveAnim() : FAnim() {
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
@Deprecated("AccurateMove is suggested.", ReplaceWith("AccurateMove()"), DeprecationLevel.WARNING)
open class SimpleMove(var x: Int, var y: Int) : MoveAnim() {

	override val delta: DoublePair
		get() {
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

	override val delta: DoublePair
		get() {
			val pair = DoublePair.from1000((now - lastRefresh) * x, (now - lastRefresh) * y)
			lastRefresh = now
			return pair
		}
}

data class DoublePair(var x: Double, var y: Double) {

	companion object {
		@JvmStatic fun from1000(x: Double, y: Double) = DoublePair(x / 1000.0, y / 1000.0)
	}

	operator fun plusAssign(d: DoublePair) {
		x += d.x
		y += d.y
	}

	operator fun plusAssign(d: Double) {
		x += d
		y += d
	}

	operator fun minusAssign(d: DoublePair) {
		x -= d.x
		y -= d.y
	}

	operator fun minusAssign(d: Double) {
		x -= d
		y -= d
	}

	operator fun timesAssign(d: Double) {
		x *= d
		y *= d
	}

	operator fun divAssign(d: Double) {
		x /= d
		y /= d
	}

	operator fun div(d: Double) = DoublePair(x / d, y / d)
	operator fun times(d: Double) = DoublePair(x * d, y * d)
	operator fun plus(d: Double) = DoublePair(x + d, y + d)
	operator fun plus(d: DoublePair) = DoublePair(x + d.x, y + d.y)
	operator fun minus(d: Double) = DoublePair(x - d, y - d)
	operator fun minus(d: DoublePair) = DoublePair(x - d.x, y - d.y)
	operator fun inc() = DoublePair(x++, y++)
	operator fun dec() = DoublePair(x--, y--)
	operator fun unaryPlus() = DoublePair(x, y)
	operator fun unaryMinus() = DoublePair(-x, -y)
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
			mx = (now - start) * ax / 2.0
			my = (now - start) * ay / 2.0
			return DoublePair.from1000((now - lastRefresh) / 1000 * mx, (now - lastRefresh) / 1000 * my)
		}

}

class CircumAnim(var x: Double, var y: Double, var angularVelocity: Double) : MoveAnim() {

	constructor(distance: DoublePair, angularVelocity: Double) :
	this(distance.x, distance.y, angularVelocity)

	private val cos: Double
		get() = Math.cos(angularVelocity)

	private val sin: Double
		get() = Math.cos(angularVelocity)

	init {
		x = -x
		y = -y
	}

	/**
	 * x' = x cos α-y sin α, y' = x sin α + y cos α
	 */
	override val delta: DoublePair
		get() = DoublePair.from1000(x * cos - y * sin, y * sin - x * cos)
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
	fun update(timeFromBegin: Double) = Unit

	override val delta: DoublePair
		get() {
			update(timeFromStart)
			return DoublePair(getXDelta(timeFromStart), getYDelta(timeFromStart))
		}

}