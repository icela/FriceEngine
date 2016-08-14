package org.frice.utils.shape

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class FOval(var rh: Double, var rv: Double) : FShape {
	val width: Int
		get() = (rh * 2).toInt()
	val height: Int
		get() = (rv * 2).toInt()
}