@file:Suppress("EqualsOrHashCode")

package org.frice.obj.sub

import org.frice.obj.FObject
import org.frice.platform.FriceImage
import org.frice.resource.image.ImageResource
import org.frice.resource.image.ImageResource.Factories.create
import org.frice.utils.shape.FRectangle
import org.frice.utils.shape.FShapeQuad

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
	id: Int = -1) : FShapeQuad, FObject(), FObject.ImageOwner {
	constructor(res: FriceImage, x: Double, y: Double) : this(create(res), x, y, -1)

	init {
		this.id = id
	}

	override val resource get() = res

	var collisionBox: FShapeQuad? = null
	override val box: FShapeQuad get() = collisionBox ?: this

	override val width: Double get() = res.image.width.toDouble()
	override val height: Double get() = res.image.height.toDouble()

	override val collideBox = FRectangle(res.image.width, res.image.height)
	override var died = false

	override fun scale(x: Double, y: Double) {
		res.image = res.image.getScaledInstance(res.image.width * x / 1000.0, res.image.height * y / 1000.0)
	}

	override val image: FriceImage get() = res.image

	override fun equals(other: Any?): Boolean {
		if (other == null || other !is FObject) return false
		if ((id != -1 && id == other.id) || this === other) return true
		return false
	}
}
