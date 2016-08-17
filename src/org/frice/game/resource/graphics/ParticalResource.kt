package org.frice.game.resource.graphics

import org.frice.game.resource.FResource
import java.awt.image.BufferedImage

/**
 * Created by ice1000 on 2016/8/17.
 *
 * @author ice1000
 * @since v0.3.1
 */
class ParticalResource(var x: Int, var y: Int, var back: ColorResource, var fore: ColorResource) : FResource {
	private val image = BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB_PRE)

	override fun getResource() = image
}