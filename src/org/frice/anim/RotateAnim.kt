package org.frice.anim

/**
 * Rotate, not supported by GameFX.
 * Created by ice1000 on 2016/8/20.
 *
 * @author ice1000
 * @since v0.4
 * @param angle radian per second
 */
open class RotateAnim(var angle: Double) : FAnim() {
	val rotate: Double
		get() {
			val ret = (now - lastRefresh) * angle / 1e3
			lastRefresh = now
			return ret
		}
}
