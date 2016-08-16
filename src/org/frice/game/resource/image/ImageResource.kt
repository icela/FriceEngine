package org.frice.game.resource.image

import org.frice.game.resource.FResource
import java.awt.image.BufferedImage

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class ImageResource : FResource {

	companion object {
		fun create(image: BufferedImage) = object : ImageResource() {
			override var image = image
		}
	}

	abstract var image: BufferedImage

	override fun getResource() = image

}