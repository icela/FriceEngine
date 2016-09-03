package org.frice.game.obj.button

import org.frice.game.event.OnMouseEvent
import org.frice.game.resource.graphics.ColorResource
import java.awt.Font

/**
 * Created by ice1000 on 2016/8/18.
 *
 * @author ice1000
 * @since v0.3.3
 */
class SimpleButton(var colorResource: ColorResource,
                   override var text: String, override var font: Font,
                   override var x: Double, override var y: Double,
                   override var width: Double, override var height: Double) : FButton, FText() {

	constructor(text: String, font: Font, x: Double, y: Double,
	            width: Double, height: Double) : this(ColorResource.GRAY, text, font, x, y, width, height)

	constructor(text: String, font: Int, x: Double, y: Double, width: Double, height: Double) :
	this(text, Font(Font.MONOSPACED, Font.BOLD, font), x, y, width, height)

	constructor(text: String, x: Double, y: Double, width: Double, height: Double) :
	this(text, Font(Font.MONOSPACED, Font.BOLD, 16), x, y, width, height)

	private var bool = false

	override var onClickListener: FButton.OnClickListener? = null

	override fun getColor() = if (bool) ColorResource(colorResource.color.darker())
	else colorResource

	override fun onMouse(e: OnMouseEvent): Boolean {
		bool = super.onMouse(e)
		return bool
	}
}