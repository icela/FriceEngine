package org.frice.obj.effects

import org.frice.obj.sub.ImageObject
import org.frice.platform.FriceImage
import org.frice.resource.graphics.ColorResource
import org.frice.resource.graphics.CurveResource
import org.frice.resource.image.ImageResource

/**
 * used to represent a curve.
 *
 * @author ice1000
 * @since v0.5.2
 */
class CurveEffect(
	res: CurveResource,
	x: Double,
	y: Double) : ImageObject(res.resource, x, y) {

	constructor(curve: (Double) -> List<Double>, x: Double, y: Double, width: Int, height: Int) :
		this(CurveResource(ColorResource.BLUE, curve::invoke, width, height), x, y)

	override val resource get() = ImageResource(image)

	override val image: FriceImage get() = res.image

	override fun scale(x: Double, y: Double) {
		res.image = image.scale(image.width * x / 1000.0, image.height * y / 1000.0)
	}
}