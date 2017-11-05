package org.frice.platform.adapter

import javafx.scene.image.WritableImage
import java.awt.image.BufferedImage

class JfxImage(image: BufferedImage) : JvmImage(image) {
	private val jfxRepresentation = WritableImage(image.width, image.height)

	init {
		for (x in 0 until width)
			for (y in 0 until height)
				jfxRepresentation.pixelWriter.setArgb(x, y, image.getRGB(x, y))
	}
}
