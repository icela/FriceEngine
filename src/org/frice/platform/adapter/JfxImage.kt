package org.frice.platform.adapter

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.WritableImage
import java.awt.image.BufferedImage

class JfxImage(image: BufferedImage) : JvmImage(image) {
	val jfxImage = WritableImage(image.width, image.height)

	init {
		SwingFXUtils.toFXImage(image, jfxImage)
	}
}
