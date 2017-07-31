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
class AccelerateMove(var ax: Double, var ay: Double) : SimpleMove(0, 0) {
	companion object Factory {
		@JvmStatic
		fun getGravity() = AccelerateMove(0.0, 10.0)

		@JvmStatic
		fun getGravity(g: Double) = AccelerateMove(0.0, g)
	}

	private var mx = 0.0
	private var my = 0.0

	override val delta: DoublePair get() {
		mx = (now - start) * ax / 2.0
		my = (now - start) * ay / 2.0
		return DoublePair.from1000((now - lastRefresh) / 1000 * mx, (now - lastRefresh) / 1000 * my)
	}

}