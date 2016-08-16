package org.frice.game.anim.move

/**
 * Simple move anim
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 * @param x pixels per second
 * @param y pixels per second
 */
open class SimpleMove(private val x: Int, private val y: Int) : MoveAnim() {
	private var cache: Double
		get() = field / 1000
	protected var now: Double
		get() = field / 1000

	init {
		now = System.currentTimeMillis().toDouble()
		cache = System.currentTimeMillis().toDouble()
	}

	override fun getDelta(): Pair<Double, Double> {
		now = System.currentTimeMillis().toDouble()
//		FLog.debug(now - cache)
		val pair = getPair(x, y)
		cache = now * 1000
		return pair
	}

	protected fun getPair(x: Int, y: Int) = Pair((now - cache) * x, (now - cache) * y)
}