package org.frice.anim

import org.frice.obj.FObject

/**
 * Rotate, not supported by GameFX.
 * Created by ice1000 on 2016/8/20.
 *
 * @author ice1000
 * @since v0.4
 * @param angle radian per second
 */
open class RotateAnim(var angle: Double) : FAnim() {
	override fun `do`(obj: FObject) {
		val ret = (now - lastRefresh) * angle / 1e3
		lastRefresh = now
		obj.rotate(ret)
	}
}
