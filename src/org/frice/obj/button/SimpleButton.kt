package org.frice.obj.button

import org.frice.event.OnClickEvent
import org.frice.event.OnMouseEvent
import org.frice.resource.graphics.ColorResource
import java.util.function.Consumer

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

	override var onClickListener: Consumer<OnClickEvent> = Consumer { }

	override fun getColor() = if (bool) ColorResource(colorResource.color.darker()) else colorResource

	override fun onMouse(e: OnMouseEvent): Boolean {
		bool = super.onMouse(e)
		return bool
	}
}