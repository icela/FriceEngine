package org.frice.game.anim.move

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */

class ForceMove(private var ax: Int, private var ay: Int) : SimpleMove(ax, ay) {
	private val start: Long
		get() = field / 1000

	init {
		start = System.currentTimeMillis()
	}

	override fun getDelta(): Pair<Double, Double> {
		ax *= start.toInt()
		ay *= start.toInt()
		return getPair(ax, ay)
	}
}