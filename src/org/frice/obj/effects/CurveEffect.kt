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
		res: org.frice.resource.graphics.CurveResource,
		override var x: Double,
		override var y: Double) : org.frice.obj.sub.ImageObject(res.getResource(), x, y) {

	constructor(curve: org.frice.obj.effects.CurveEffect.FCurve, x: Double, y: Double, width: Int, height: Int) :
			this(org.frice.resource.graphics.CurveResource(org.frice.resource.graphics.ColorResource.BLUE, curve::call, width, height), x, y)

	override fun getResource() = org.frice.resource.image.ImageResource(image)

	override val image: org.frice.platform.FriceImage get() = res.image

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