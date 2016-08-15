package org.frice.game.anim

import org.frice.game.utils.message.log.FLog

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 * @param x pixels per second
 * @param y pixels per second
 */
class SimpleMove(private val x: Int, private val y: Int) : MoveAnim {
	private var cache: Double
		get() = field / 1000
	private var now: Double
		get() = field / 1000

	init {
		now = System.currentTimeMillis().toDouble()
		cache = System.currentTimeMillis().toDouble()
	}

	override fun getDelta(): Pair<Double, Double> {
		now = System.currentTimeMillis().toDouble()
		FLog.debug(now - cache)
		val pair = Pair((now - cache) * x,
				(now - cache) * y)
		cache = now * 1000
		return pair
	}
}