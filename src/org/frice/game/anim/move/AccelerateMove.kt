package org.frice.game.anim.move

/**
 * Move with force (accelerate), give accelerate value to ax and by
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 * @param ax accelerate on x
 * @param ay accelerate on y
 */

class AccelerateMove(private var ax: Double, private var ay: Double) : SimpleMove(0, 0) {
	companion object {
		@JvmStatic fun getGravity() = AccelerateMove(0.0, 10.0)
		@JvmStatic fun getGravity(g: Double) = AccelerateMove(0.0, g)
	}

	var mx = 0.0
	var my = 0.0

	override fun getDelta(): Pair<Double, Double> {
		now = System.currentTimeMillis().toDouble()
		mx = (now - start) * ax
		my = (now - start) * ay
//		FLog.debug("my = $my, now = $now, start = $start")
		return getPair(mx.toInt(), my.toInt())
	}
}