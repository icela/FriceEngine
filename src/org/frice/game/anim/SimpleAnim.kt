package org.frice.game.anim

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 * @param x pixels per millis second
 * @param y pixels per millis second
 */
class SimpleAnim(private val x: Double, private val y: Double) : FAnim {
	private var cache: Long

	init {
		cache = System.currentTimeMillis()
	}

	override fun move(): Pair<Double, Double> {
		val pair = Pair((System.currentTimeMillis() - cache) * x, (System.currentTimeMillis() - cache) * y)
		cache = System.currentTimeMillis()
		return pair
	}
}