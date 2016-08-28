package org.frice.game.resource.graphics

import org.frice.game.resource.FResource
import org.frice.game.utils.kotlin.forceRun
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
		(0..width step 1).forEach { x ->
			listOf(0.0, 0.2, 0.4, 0.6, 0.8).forEach { d ->
				forceRun { image.setRGB((x + d).toInt(), f(x + d).toInt(), colorResource.color.rgb) }
			}
		}
	}

	override fun getResource() = image
}