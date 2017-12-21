@file:Suppress("EqualsOrHashCode")

package org.frice.obj.sub

import org.frice.obj.FObject
import org.frice.platform.FriceImage
import org.frice.platform.owner.ImageOwner
import org.frice.resource.image.ImageResource
import org.frice.resource.image.ImageResource.Factories.create

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
	x: Double = 0.0,
	y: Double = 0.0,
	id: Int = -1) : FObject(x, y), ImageOwner {
	constructor(res: FriceImage, x: Double, y: Double) : this(create(res), x, y, -1)

	init {
		this.id = id
	}

	override val resource get() = res
	override var died = false
	override val image: FriceImage get() = res.image

	override fun scale(x: Double, y: Double) {
		res.image = res.image.scale(res.image.width * x / 1000.0, res.image.height * y / 1000.0)
	}

	override fun equals(other: Any?): Boolean {
		if (other == null || other !is FObject) return false
		if ((id != -1 && id == other.id) || this === other) return true
		return false
	}
}
