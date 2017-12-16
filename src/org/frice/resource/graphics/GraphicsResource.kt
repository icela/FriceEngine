package org.frice.resource.graphics

import org.frice.platform.FriceImage
import org.frice.platform.adapter.JvmImage
import org.frice.resource.FResource
import org.frice.utils.forceRun


/**
 * Created by ice1000 on 2016/8/27.
 *
 * @author ice1000
 * @since v0.4
 */
class FunctionResource(
	color: ColorResource,
	val f: (Double) -> Double,
	width: Int, height: Int) : FResource {

	private val image: FriceImage

	init {
		image = JvmImage(width, height)
		var lastTime = f(0.0)
		var thisTime: Double
		for (x in 0 until width) {
			thisTime = f(x.toDouble())
			if (thisTime < height)
				image[x, thisTime.toInt()] = color
			if (Math.abs(thisTime - lastTime) >= 1.0) forceRun {
				for (i in Math.min(thisTime, lastTime).toInt()..Math.max(thisTime, lastTime).toInt()) image[x, i] = color
			}
			lastTime = thisTime
		}
	}

	override val resource get() = image
}

/**
 * used to represent a Curve(something like circle).
 *
 * @author ice1000
 * @since v0.4
 */
class CurveResource(color: ColorResource, val f: (Double) -> List<Double>, width: Int, height: Int) : FResource {
	private val image: FriceImage

	init {
		image = JvmImage(width, height)
		for (x in 0..width) for (y in f(x.toDouble())) {
			forceRun { image[x, y.toInt()] = color }
		}
	}

	override val resource get() = image
}

