package org.frice.obj.button

import org.frice.event.MOUSE_PRESSED
import org.frice.event.OnMouseEvent
import org.frice.obj.FObject
import org.frice.platform.FriceImage
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
	val imageNormal: ImageResource,
	val imagePressed: ImageResource = imageNormal,
	override var x: Double,
	override var y: Double) : FObject.ImageOwner, FButton {

	override fun buttonPressed(e: OnMouseEvent) {
		bool = e.type == MOUSE_PRESSED
	}

	private var bool = false
	override var w: Double = super.w
	override var h: Double = super.h
	override var rotate = 0.0
	override var onMouseListener: Consumer<OnMouseEvent>? = null
	override var collisionBox: FShapeQuad? = null
	override val image: FriceImage get () = if (bool) imagePressed.image else imageNormal.image
}