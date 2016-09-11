package org.frice.game.anim.move

import org.frice.game.anim.FAnim

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 */
abstract class MoveAnim : FAnim() {
	abstract val delta: Pair<Double, Double>

	protected var cache: Double
		get() = field / 1000

	init {
		cache = System.currentTimeMillis().toDouble()
	}
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

	override val delta: Pair<Double, Double>
		get() {
			val pair = getPair(x, y)
			cache = now * 1000
			return pair
		}

	protected fun getPair(x: Int, y: Int) = Pair((now - cache) * x, (now - cache) * y)
}

class AccurateMove(var x: Double, var y: Double): MoveAnim() {

	override val delta: Pair<Double, Double>
		get() {
			val pair = Pair((now - cache) * x, (now - cache) * y)
			cache = now * 1000
			return pair
		}
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

	override val delta: Pair<Double, Double>
		get() {
			mx = (now - start) * ax
			my = (now - start) * ay
			return getPair(mx.toInt(), my.toInt())
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
		get() = System.currentTimeMillis().toDouble() / 1000 - start

	abstract fun getXDelta(timeFromBegin: Double): Double
	abstract fun getYDelta(timeFromBegin: Double): Double

	override val delta: Pair<Double, Double>
		get() = Pair(getXDelta(timeFromStart), getYDelta(timeFromStart))

}