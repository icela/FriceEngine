package org.frice.anim.rotate

import org.frice.anim.FAnim
import org.frice.obj.FObject

/**
 * Scale with acceleration
 *
 * @author ice1000
 * @since v1.7.9
 * @param a pixels per second squared
 */
class AccelerateRotate(var a: Double) : FAnim() {
	override fun `{-# do #-}`(obj: FObject) {
		val deltaTime = (now - lastRefresh) / 1e6
		lastRefresh = now
		obj.rotate(deltaTime * ((now - start) * a / 2))
	}
}