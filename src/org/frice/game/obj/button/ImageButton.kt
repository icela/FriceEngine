package org.frice.game.obj.button

import org.frice.game.event.OnMouseEvent
import org.frice.game.obj.FObject
import org.frice.game.resource.image.ImageResource

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

	override fun getImage() = if (bool) imagePressed.image else imageNormal.image

	override fun onMouse(e: OnMouseEvent): Boolean {
		bool = super.onMouse(e)
		return bool
	}
}