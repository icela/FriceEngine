package org.frice.game.utils.shape

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
open class FOval(var rh: Double, var rv: Double) : FShape {
	private var px = 1.0
	private var py = 1.0

	override var width = (rv * 2).toInt()
		get() = (rh * 2 * px).toInt()
		set(value) {
			px = (value / field).toDouble()
//			field = value
		}
	override var height = (rv * 2).toInt()
		get() = (rv * 2 * py).toInt()
		set(value) {
			py = (value / field).toDouble()
//			field = value
		}
}