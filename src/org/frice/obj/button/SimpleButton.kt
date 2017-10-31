package org.frice.obj.button

import org.frice.event.OnClickEvent
import org.frice.event.OnMouseEvent
import org.frice.resource.graphics.ColorResource

/**
 * Created by ice1000 on 2016/8/18.
 *
 * @author ice1000
 * @since v0.3.3
 */
open class SimpleButton
@JvmOverloads
constructor(
		var colorResource: org.frice.resource.graphics.ColorResource = org.frice.resource.graphics.ColorResource.GRAY,
		override var text: String,
		override var x: Double,
		override var y: Double,
		override var width: Double,
		override var height: Double) : org.frice.obj.button.FButton, org.frice.obj.button.FText() {

	private var bool = false

	override var onClickListener: (org.frice.event.OnClickEvent) -> Unit = { }

	override fun getColor() = if (bool) org.frice.resource.graphics.ColorResource(colorResource.color.darker()) else colorResource

	override fun onMouse(e: org.frice.event.OnMouseEvent): Boolean {
		bool = super.onMouse(e)
		return bool
	}
}