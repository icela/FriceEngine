package org.frice.game.utils.shape

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
open class FOval(var rh: Double, var rv: Double) : FShape {
	override var width = (rh * 2).toInt()
	override var height = (rv * 2).toInt()
}