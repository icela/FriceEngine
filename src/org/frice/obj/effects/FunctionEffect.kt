package org.frice.obj.effects

import org.frice.obj.sub.ImageObject
import org.frice.platform.FriceImage
import org.frice.resource.graphics.ColorResource
import org.frice.resource.graphics.FunctionResource
import org.frice.resource.image.ImageResource

/**
 * Tested, Work stably.
 * Created by ice1000 on 2016/8/24.
 *
 * @author ice1000
 * @since v0.4.1
 */
class FunctionEffect(
	res: FunctionResource,
	x: Double,
	y: Double) : ImageObject(res.resource, x, y) {

	constructor(function: (Double) -> Double, x: Double, y: Double, width: Int, height: Int) :
		this(FunctionResource(ColorResource.BLUE, function::invoke, width, height), x, y)

	override val width: Double get() = res.image.width.toDouble()
	override val height: Double get() = res.image.height.toDouble()

	override val resource get() = ImageResource(image)

	override val image: FriceImage get() = res.image

	override fun scale(x: Double, y: Double) {
		res.image = image.scale(image.width * x / 1000.0, image.height * y / 1000.0)
	}
}