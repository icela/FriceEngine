package org.frice.game.obj.effects

import org.frice.game.obj.AbstractObject
import org.frice.game.obj.sub.ImageObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.graphics.FunctionResource
import org.frice.game.resource.graphics.ParticleResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FRectangle
import java.awt.Image
import java.awt.image.BufferedImage

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.5
 */
class LineEffect(var colorResource: ColorResource, override var x: Double, override var y: Double,
                 var x2: Double, var y2: Double) : AbstractObject {

	override var rotate = 0.0

	constructor(x: Double, y: Double, x2: Double, y2: Double) : this(ColorResource.BLACK, x, y, x2, y2)
}

/**
 * Created by ice1000 on 2016/8/17.
 * @author ice1000
 * @since 0.3.2
 */
class ParticleEffect(private var resource: ParticleResource, override var x: Double, override var y: Double) :
		ImageObject(resource.getResource(), x, y) {
	override val image: BufferedImage
		get() = resource.getResource()

	override val collideBox = FRectangle(x.toInt(), y.toInt())

	override val width: Double
		get() = resource.width.toDouble()
	override val height: Double
		get() = resource.height.toDouble()

	override fun getResource() = ImageResource.create(image)

	override fun scale(p: Pair<Double, Double>) {
		resource.width = (resource.width * p.first).toInt()
		resource.height = (resource.height * p.second).toInt()
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
class FunctionEffect(res: FunctionResource, override var x: Double, override var y: Double) :
		ImageObject(res.getResource(), x, y) {

	constructor(FFunction: FFunction, x: Double, y: Double, width: Int, height: Int) :
	this(FunctionResource(ColorResource.BLUE, { x -> FFunction.call(x) }, width, height), x, y)

	override val width: Double
		get() = res.image.width.toDouble()
	override val height: Double
		get() = res.image.height.toDouble()

	override fun getResource() = ImageResource.create(image)

	override val image: BufferedImage
		get() = res.image

	override fun scale(p: Pair<Double, Double>) {
		res.image = res.image.getScaledInstance((res.image.width * p.first).toInt(),
				(res.image.height * p.second).toInt(), Image.SCALE_DEFAULT) as BufferedImage
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
