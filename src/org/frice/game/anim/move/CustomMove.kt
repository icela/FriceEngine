package org.frice.game.anim.move

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

	override fun getDelta() = Pair(getXDelta(timeFromStart), getYDelta(timeFromStart))
}