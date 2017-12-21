package org.frice.obj.effects

import org.frice.obj.PhysicalObject
import org.frice.obj.sub.ImageObject
import org.frice.platform.FriceImage
import org.frice.platform.owner.ColorOwner
import org.frice.resource.graphics.ColorResource
import org.frice.resource.graphics.ColorResource.Companion.BLACK
import org.frice.resource.graphics.ParticleResource
import org.frice.resource.image.ImageResource

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.5
 */
class LineEffect
@JvmOverloads
constructor(
	override var color: ColorResource = BLACK,
	x: Double,
	y: Double,
	var x2: Double,
	var y2: Double) : PhysicalObject(x, y), ColorOwner {
	override val width: Double get() = Math.abs(x2 - x)
	override val height: Double get() = Math.abs(y2 - y)
}

/**
 * Created by ice1000 on 2016/8/17.
 * @author ice1000
 * @since 0.3.2
 */
class ParticleEffect(
	private var pRes: ParticleResource,
	x: Double,
	y: Double) : ImageObject(pRes.resource, x, y) {
	override val image: FriceImage get() = pRes.resource

	override val width: Double get() = pRes.width.toDouble()
	override val height: Double get() = pRes.height.toDouble()

	override val resource: ImageResource get() = ImageResource(image)

	override fun scale(x: Double, y: Double) {
		pRes.width = (pRes.width * x / 1000.0).toInt()
		pRes.height = (pRes.height * y / 1000.0).toInt()
	}

	//	override fun collides(other: Collidable): Boolean = false
}
