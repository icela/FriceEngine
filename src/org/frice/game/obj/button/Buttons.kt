package org.frice.game.obj.button

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.obj.FContainer
import org.frice.game.obj.FObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import java.awt.Font
import java.awt.image.BufferedImage

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.2
 */

interface FButton : FContainer {
	var onClickListener: OnClickListener?

	infix fun onClick(e: OnClickEvent) = onClickListener?.onClick(e)

	/**
	 * @return true means pressed
	 */
	infix fun onMouse(e: OnMouseEvent) = (e.type() == OnMouseEvent.MOUSE_PRESSED)

	/**
	 * Created by ice1000 on 2016/8/19.
	 * @author ice1000
	 * @since v0.4
	 */
	interface OnClickListener {
		fun onClick(e: OnClickEvent)
	}

	/**
	 * Created by ice1000 on 2016/8/19.
	 * @author ice1000
	 * @since v0.4
	 */
	interface OnMouseListener {
		fun onMouse(e: OnMouseEvent)
	}
}


/**
 * Created by ice1000 on 2016/9/3 0003.
 *
 * @author ice1000
 * @since v0.5
 */
class ImageButton(val imageNormal: ImageResource, val imagePressed: ImageResource,
                  override var x: Double, override var y: Double,
                  override var width: Double, override var height: Double) : FButton, FObject.ImageOwner {
	constructor(image: ImageResource, x: Double, y: Double, width: Double, height: Double) :
	this(image, image, x, y, width, height)

	override var onClickListener: FButton.OnClickListener? = null

	private var bool = false

	override val image: BufferedImage
		get () = if (bool) imagePressed.image else imageNormal.image

	override fun onMouse(e: OnMouseEvent): Boolean {
		bool = super.onMouse(e)
		return bool
	}
}

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

	override fun getColor() = if (bool) ColorResource(colorResource.color.darker()) else colorResource

	override fun onMouse(e: OnMouseEvent): Boolean {
		bool = super.onMouse(e)
		return bool
	}
}