package org.frice.game.obj.effects

import org.frice.game.obj.sub.ImageObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.graphics.FunctionResource
import org.frice.game.resource.image.ImageResource
import java.awt.Image
import java.awt.image.BufferedImage

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

	override fun getResource() = ImageResource.create(getImage())

	override fun getImage() = res.image

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
