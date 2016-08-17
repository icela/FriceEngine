package org.frice.game.resource.graphics

import org.frice.game.resource.FResource
import java.awt.image.BufferedImage

/**
 * Particle effects
 * Created by ice1000 on 2016/8/17.
 *
 * @author ice1000
 * @since v0.3.1
 */
class ParticleResource(var x: Int, var y: Int,
                       var back: ColorResource,
                       var fore: ColorResource,
                       val persentage: Double) : FResource {
	constructor(x: Int, y: Int, back: ColorResource, fore: ColorResource) : this(x, y, back, fore, 0.5)
	constructor(x: Int, y: Int) : this(x, y, ColorResource.BLACK, ColorResource.WHITE, 0.5)

	private val image = BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB_PRE)

	override fun getResource() = image
}