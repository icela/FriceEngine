package org.frice.game.obj.effects

import org.frice.game.obj.sub.ImageObject
import org.frice.game.platform.FriceImage
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.graphics.CurveResource
import org.frice.game.resource.image.ImageResource

/**
 * used to represent a curve.
 *
 * @author ice1000
 * @since v0.5.2
 */
class CurveEffect(
		res: CurveResource,
		override var x: Double,
		override var y: Double) : ImageObject(res.getResource(), x, y) {

	constructor(curve: FCurve, x: Double, y: Double, width: Int, height: Int) :
			this(CurveResource(ColorResource.BLUE, curve::call, width, height), x, y)

	override fun getResource() = ImageResource(image)

	override val image: FriceImage get() = res.image

	override fun scale(x: Double, y: Double) {
		res.image = image.getScaledInstance(image.width * x / 1000.0,
				image.height * y / 1000.0)
	}

	/**
	 * for java.
	 * this can be represent as a lambda in j8.
	 *
	 * @author ice1000
	 * @since v0.5.2
	 */
	interface FCurve {
		fun call(x: Double): List<Double>
	}
}