package org.frice.game.obj.button

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.obj.AbstractObject
import org.frice.game.obj.FContainer
import org.frice.game.obj.FObject
import org.frice.game.platform.FriceImage
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.2
 */

interface FButton : FContainer, AbstractObject {
	var onClickListener: (OnClickEvent) -> Unit

	infix fun onClick(e: OnClickEvent) = onClickListener.invoke(e)

	/**
	 * @return true means pressed
	 */
	infix fun onMouse(e: OnMouseEvent) = (e.type() == OnMouseEvent.MOUSE_PRESSED)
}


/**
 * Created by ice1000 on 2016/9/3 0003.
 *
 * @author ice1000
 * @since v0.5
 */
class ImageButton
@JvmOverloads
constructor(
		val imageNormal: ImageResource,
		val imagePressed: ImageResource = imageNormal,
		x: Double,
		y: Double) : FObject.ImageOwner, SimpleButton(
		text = "",
		x = x,
		y = y,
		width = imageNormal.image.width.toDouble(),
		height = imageNormal.image.height.toDouble()) {

	override var rotate = 0.0

	override var onClickListener: (OnClickEvent) -> Unit = { }

	private var bool = false

	override val image: FriceImage get () = if (bool) imagePressed.image else imageNormal.image
}

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