package org.frice.game.obj.button

import org.frice.game.event.OnClickEvent
import org.frice.game.obj.FObject
import org.frice.game.platform.FriceImage
import org.frice.game.resource.image.ImageResource

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