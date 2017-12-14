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
