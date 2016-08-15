package org.frice.game.anim

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 * @param x pixels per millis second
 * @param y pixels per millis second
 */
class SimpleMove(private val x: Int, private val y: Int) : MoveAnim {
	private var cache: Long

	init {
		cache = System.currentTimeMillis()
	}

	override fun getDelta(): Pair<Int, Int> {
		val pair = Pair((System.currentTimeMillis() - cache).toInt() * x,
				(System.currentTimeMillis() - cache).toInt() * y)
		cache = System.currentTimeMillis()
		return pair
	}
}