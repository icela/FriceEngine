package org.frice.obj.button

import org.frice.event.MOUSE_PRESSED
import org.frice.event.OnMouseEvent
import org.frice.platform.FriceImage
import org.frice.platform.owner.ImageOwner
import org.frice.resource.image.ImageResource
import org.frice.utils.shape.FShapeQuad
import java.util.function.Consumer

/**
 * Created by ice1000 on 2016/9/3 0003.
 *
 * @author ice1000
 * @since v0.5
 */
class ImageButton
@JvmOverloads
constructor(
	var imageNormal: ImageResource,
	var imagePressed: ImageResource = imageNormal,
	override var x: Double,
	override var y: Double) : ImageOwner, FButton {

	override fun buttonPressed(e: OnMouseEvent) {
		bool = e.type == MOUSE_PRESSED
	}

	private var bool = false
	override var width: Double = super.width
	override var height: Double = super.height
	override var rotate = 0.0
	override var isVisible = true
	override var died = false
	override var onMouseListener: Consumer<OnMouseEvent>? = null
	var collisionBox: FShapeQuad? = null
	override val box: FShapeQuad get() = collisionBox ?: this
	override val image: FriceImage get () = if (bool) imagePressed.image else imageNormal.image
}