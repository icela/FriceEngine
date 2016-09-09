package org.frice.game.resource.graphics

import org.frice.game.resource.FResource
import org.frice.game.utils.misc.forceRun
import java.awt.image.BufferedImage

/**
 * Created by ice1000 on 2016/8/27.
 *
 * @author ice1000
 * @since v0.4
 */
class FunctionResource(colorResource: ColorResource, val f: (Double) -> Double, width: Int, height: Int) : FResource {

	private val image: BufferedImage

	init {
		image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		var lastTime = f(0.0)
		var thisTime: Double
		(0..width step 1).forEach { x ->
			thisTime = f(x.toDouble())
			forceRun { image.setRGB(x.toInt(), thisTime.toInt(), colorResource.color.rgb) }
			if (Math.abs(thisTime - lastTime) >= 1.0) forceRun {
				(Math.min(thisTime, lastTime).toInt()..Math.max(thisTime, lastTime).toInt()).forEach { i ->
					image.setRGB(x, i, colorResource.color.rgb)
				}
			}
			lastTime = thisTime
		}
	}

	override fun getResource() = image
}