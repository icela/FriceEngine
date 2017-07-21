package org.frice.game.obj.button

import org.frice.game.obj.AbstractObject
import org.frice.game.resource.graphics.ColorResource

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.4
 */
abstract class FText : AbstractObject {
	open var text = ""
	override var rotate = 0.0

	abstract fun getColor(): ColorResource
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
		var colorResource: ColorResource = ColorResource.DARK_GRAY,
		override var text: String,
		override var x: Double, override var y: Double) : FText() {

	override fun getColor() = colorResource
}