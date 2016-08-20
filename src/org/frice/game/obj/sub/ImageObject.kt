package org.frice.game.obj.sub

import org.frice.game.obj.FObject
import org.frice.game.obj.PhysicalObject
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FRectangle
import java.awt.Image
import java.awt.image.BufferedImage

/**
 * Base GameObject class
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class ImageObject(var res: ImageResource, override var id: Int,
                       override var x: Double, override var y: Double) : FObject() {
	constructor(res: ImageResource, id: Int) : this(res, id, 0.0, 0.0)

	constructor(res: ImageResource) : this(res, -1, 0.0, 0.0)

	constructor(res: ImageResource, x: Double, y: Double) : this(res, -1, x, y)

	constructor(res: BufferedImage, x: Double, y: Double) : this(ImageResource.create(res), -1, x, y)

	override fun getResource() = res

	override fun isCollide(other: PhysicalObject) = when (other) {
		is ShapeObject -> when (other.collideBox) {
			is FRectangle -> rectCollide(this, other)
		// TODO
			else -> rectCollide(this, other)
		}
		is ImageObject -> rectCollide(this, other)
		else -> false
	}

	override val width: Double
		get() = res.image.width.toDouble()
	override val height: Double
		get() = res.image.height.toDouble()

	override val collideBox = FRectangle(res.image.width, res.image.height)
	override var died = false

	override fun scale(p: Pair<Double, Double>) {
		res.image = res.image.getScaledInstance((res.image.width * p.first).toInt(),
				(res.image.height * p.second).toInt(), Image.SCALE_DEFAULT) as BufferedImage
	}

	/**
	 * @return returns an image instance of this res
	 */
	open fun getImage() = res.image

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is ImageObject) return false
		if ((id != -1 && id == other.id) ||
				res == other.res && x == other.x && y == other.y) return true
		return false
	}

	override fun hashCode(): Int {
		var result = res.hashCode()
		result = 31 * result + id
		result = 31 * result + x.hashCode()
		result = 31 * result + y.hashCode()
		result = 31 * result + anims.hashCode()
		return result
	}
}