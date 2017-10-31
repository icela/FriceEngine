package org.frice.anim

/**
 * Created by ice1000 on 2016/8/20.
 * @author ice1000
 * @since v0.4
 */
open class RotateAnim(var angle: Double) : org.frice.anim.FAnim() {
	val rotate: Double
		get() = (now - start) * angle
}