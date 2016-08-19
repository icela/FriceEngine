package org.frice.game.obj.button

import org.frice.game.resource.graphics.ColorResource
import java.awt.Font
import java.awt.Toolkit

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.4
 */
class SimpleText(var colorResource: ColorResource, override var font: Font,
                 override var text: String, override var x: Double, override var y: Double) : FText {

	constructor(text: String, font: Font, x: Double, y: Double) :
	this(ColorResource.DARK_GRAY, font, text, x, y)

	constructor(text: String, font: Int, x: Double, y: Double) :
	this(ColorResource.DARK_GRAY, Font(Font.MONOSPACED, Font.BOLD, font), text, x, y)

	constructor(text: String, x: Double, y: Double) : this(text, 16, x, y)

	override fun getColor() = colorResource
}