package org.frice.game.obj.sub

import org.frice.game.obj.CollideBox
import org.frice.game.obj.FObject
import org.frice.game.platform.FriceImage
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FRectangle

/**
 * Base GameObject class
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class ImageObject
@JvmOverloads
constructor(
		var res: ImageResource,
		override var x: Double = 0.0,
		override var y: Double = 0.0,
		override var id: Int = -1) : FObject(), FObject.ImageOwner {
	constructor(res: FriceImage, x: Double, y: Double) : this(ImageResource.create(res), x, y, -1)

	override fun getResource() = res

	override fun isCollide(other: CollideBox): Boolean = when (other) {
		is ShapeObject -> when (other.collideBox) {
			is FRectangle -> this rectCollideRect other
		// TODO
			else -> this rectCollideRect other
		}
		is ImageObject -> this rectCollideRect other
		else -> false
	}

	override val width: Double get() = res.image.width.toDouble()
	override val height: Double get() = res.image.height.toDouble()

	override val collideBox = FRectangle(res.image.width, res.image.height)
	override var died = false

	override fun scale(x: Double, y: Double) {
		res.image = res.image.getScaledInstance(res.image.width * x / 1000.0, res.image.height * y / 1000.0)
	}

	override val image: FriceImage get() = res.image

	override fun equals(other: Any?): Boolean {
		if (other == null || other !is ImageObject) return false
		if ((id != -1 && id == other.id) || this === other) return true
		return false
	}
}
