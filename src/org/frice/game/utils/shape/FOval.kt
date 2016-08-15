package org.frice.game.utils.shape

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
open class FOval(var rh: Double, var rv: Double) : FShape {
	override var width = (rv * 2).toInt()
	override var height = (rh * 2).toInt()
}