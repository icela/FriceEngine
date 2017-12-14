package org.frice.anim.scale

import org.frice.anim.FAnim
import org.frice.obj.FObject

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 * @param x size proportion on x axis, per second
 * @param y size proportion on y axis, per second
 */
class SimpleScale(var x: Double, var y: Double) : FAnim() {
	override fun `{-# do #-}`(obj: FObject) {
		val deltaTime = (now - lastRefresh) / 1e3
		lastRefresh = now
		obj.scale(deltaTime * x, deltaTime * x)
	}
}
/**
 * Scale with acceleration, give accelerate value to ax and by
 *
 * @author ice1000
 * @since v1.7.9
 * @param ax size proportion on x axis, per second squared
 * @param ay size proportion on y axis, per second squared
 */
class AccelerateScale(var ax: Double, var ay: Double) : FAnim() {
	private var mx = 0.0
	private var my = 0.0
	override fun `{-# do #-}`(obj: FObject) {
		mx = (now - start) * ax / 2
		my = (now - start) * ay / 2
		val deltaTime = (now - lastRefresh) / 1e6
		lastRefresh = now
		obj.scale(deltaTime * mx, deltaTime * my)
	}
}