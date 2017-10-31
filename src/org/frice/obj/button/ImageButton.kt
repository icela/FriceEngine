package org.frice.obj.button

import org.frice.event.OnClickEvent
import org.frice.obj.FObject
import org.frice.platform.FriceImage
import org.frice.resource.image.ImageResource

/**
 * Created by ice1000 on 2016/9/3 0003.
 *
 * @author ice1000
 * @since v0.5
 */
class ImageButton
@JvmOverloads
constructor(
		val imageNormal: org.frice.resource.image.ImageResource,
		val imagePressed: org.frice.resource.image.ImageResource = imageNormal,
		x: Double,
		y: Double) : org.frice.obj.FObject.ImageOwner, org.frice.obj.button.SimpleButton(
		text = "",
		x = x,
		y = y,
		width = imageNormal.image.width.toDouble(),
		height = imageNormal.image.height.toDouble()) {

	override var rotate = 0.0

	override var onClickListener: (org.frice.event.OnClickEvent) -> Unit = { }

	private var bool = false

	override val image: org.frice.platform.FriceImage get () = if (bool) imagePressed.image else imageNormal.image
}