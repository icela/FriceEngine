package org.frice.obj.button

import org.frice.obj.AbstractObject
import org.frice.resource.graphics.ColorResource

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.4
 */
abstract class FText : org.frice.obj.AbstractObject {
	open var text = ""
	open var textSize = 16
//	open var fontName = "Consolas"
	override var rotate = 0.0

	abstract fun getColor(): org.frice.resource.graphics.ColorResource
}

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.4
 */
class SimpleText
@JvmOverloads
constructor(
		var colorResource: org.frice.resource.graphics.ColorResource = org.frice.resource.graphics.ColorResource.DARK_GRAY,
		override var text: String,
		override var x: Double,
		override var y: Double) : org.frice.obj.button.FText() {

	override fun getColor() = colorResource
}
