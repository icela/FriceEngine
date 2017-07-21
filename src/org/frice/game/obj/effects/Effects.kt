package org.frice.game.obj.effects

import org.frice.game.obj.AbstractObject
import org.frice.game.obj.sub.ImageObject
import org.frice.game.platform.FriceImage
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.graphics.CurveResource
import org.frice.game.resource.graphics.FunctionResource
import org.frice.game.resource.graphics.ParticleResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FRectangle

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.5
 */
class LineEffect(
		var colorResource: ColorResource,
		override var x: Double,
		override var y: Double,
		var x2: Double,
		var y2: Double) : AbstractObject {

	override var rotate = 0.0

	constructor(x: Double, y: Double, x2: Double, y2: Double) : this(ColorResource.BLACK, x, y, x2, y2)
}

/**
 * Created by ice1000 on 2016/8/17.
 * @author ice1000
 * @since 0.3.2
 */
class ParticleEffect(
		private var resource: ParticleResource,
		override var x: Double,
		override var y: Double) : ImageObject(resource.getResource(), x, y) {
	override val image: FriceImage get() = resource.getResource()

	override val collideBox = FRectangle(x.toInt(), y.toInt())

	override val width: Double get() = resource.width.toDouble()
	override val height: Double get() = resource.height.toDouble()

	override fun getResource() = ImageResource(image)

	override fun scale(x: Double, y: Double) {
		resource.width = (resource.width * x / 1000.0).toInt()
		resource.height = (resource.height * y / 1000.0).toInt()
	}

	//	override fun isCollide(other: CollideBox): Boolean = false
}

/**
 * Tested, Work stably.
 * Created by ice1000 on 2016/8/24.
 *
 * @author ice1000
 * @since v0.4.1
 */
class FunctionEffect(
		res: FunctionResource,
		override var x: Double,
		override var y: Double) : ImageObject(res.getResource(), x, y) {

	constructor(function: FFunction, x: Double, y: Double, width: Int, height: Int) :
			this(FunctionResource(ColorResource.BLUE, function::call, width, height), x, y)

	override val width: Double get() = res.image.width.toDouble()
	override val height: Double get() = res.image.height.toDouble()

	override fun getResource() = ImageResource(image)

	override val image: FriceImage get() = res.image

	override fun scale(x: Double, y: Double) {
		res.image = image.getScaledInstance(image.width * x / 1000.0, image.height * y / 1000.0)
	}

	/**
	 * Created by ice1000 on 2016/8/27.
	 *
	 * @author ice1000
	 * @since v0.4
	 */
	interface FFunction {
		fun call(x: Double): Double
	}
}

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
