package org.frice.game.obj.button

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.resource.graphics.ColorResource

/**
 * Created by ice1000 on 2016/8/18.
 *
 * @author ice1000
 * @since v0.3.3
 */
open class SimpleButton
@JvmOverloads
constructor(
		var colorResource: ColorResource = ColorResource.GRAY,
		override var text: String,
		override var x: Double,
		override var y: Double,
		override var width: Double,
		override var height: Double) : FButton, FText() {

	private var bool = false

	override var onClickListener: (OnClickEvent) -> Unit = { }

	override fun getColor() = if (bool) ColorResource(colorResource.color.darker()) else colorResource

	override fun onMouse(e: OnMouseEvent): Boolean {
		bool = super.onMouse(e)
		return bool
	}
}