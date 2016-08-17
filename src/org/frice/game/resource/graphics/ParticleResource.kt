package org.frice.game.resource.graphics

import org.frice.game.Game
import org.frice.game.resource.FResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.loop
import java.awt.image.BufferedImage
import java.util.*

/**
 * Particle effects
 * Created by ice1000 on 2016/8/17.
 *
 * @author ice1000
 * @since v0.3.2
 */
class ParticleResource(val game: Game,
                       var x: Int, var y: Int,
                       val back: FResource,
                       var fore: ColorResource,
                       val percentage: Double) : FResource {
	constructor(game: Game, x: Int, y: Int, back: ColorResource, fore: ColorResource) :
	this(game, x, y, back, fore, 0.5)

	constructor(game: Game, x: Int, y: Int) :
	this(game, x, y, ColorResource.BLACK, ColorResource.WHITE, 0.5)

	private val image = BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB_PRE)
	private val random = Random(Random().nextLong())

	private fun drawBackground() {
		val g = image.graphics
		when (back) {
			is ColorResource -> {
				g.color = back.color
				g.fillRect(0, 0, x, y)
			}
			is ImageResource -> g.drawImage(back.image, 0, 0, x, y, game)
		}
	}

	init {
		drawBackground()
	}

	override fun getResource() = image.apply {
		drawBackground()
		loop((image.width * image.height * percentage).toInt()) {
			image.setRGB(random.nextInt(x), random.nextInt(y), fore.color.rgb)
		}
	}
}