package org.frice.game.resource.image

import org.frice.game.resource.FResource
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL

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

		fun fromImage(image: BufferedImage) = create(image)
		fun fromFile(file: File) = FileImageResource(file)
		fun fromPath(path: String) = FileImageResource(path)
		fun fromWeb(url: String) = WebImageResource(url)
		fun fromURL(url: URL) = WebImageResource(url)
	}

	abstract var image: BufferedImage

	override fun getResource() = image

}