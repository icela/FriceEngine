package org.frice.game.utils.shape

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class FCircle(var r: Double) : FShape {
	val width: Int
		get() = (r * 2).toInt()
}