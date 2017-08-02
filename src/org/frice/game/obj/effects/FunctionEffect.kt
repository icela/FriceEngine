package org.frice.game.obj.effects

import org.frice.game.obj.sub.ImageObject
import org.frice.game.platform.FriceImage
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.graphics.FunctionResource
import org.frice.game.resource.image.ImageResource

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

	constructor(function: Function1<Double, Double>, x: Double, y: Double, width: Int, height: Int) :
			this(FunctionResource(ColorResource.BLUE, function::invoke, width, height), x, y)

	override val width: Double get() = res.image.width.toDouble()
	override val height: Double get() = res.image.height.toDouble()

	override fun getResource() = ImageResource(image)

	override val image: FriceImage get() = res.image

	override fun scale(x: Double, y: Double) {
		res.image = image.getScaledInstance(image.width * x / 1000.0, image.height * y / 1000.0)
	}
}