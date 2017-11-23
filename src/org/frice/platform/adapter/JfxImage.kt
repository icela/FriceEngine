package org.frice.platform.adapter

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.WritableImage
import org.frice.resource.graphics.ColorResource
import java.awt.image.BufferedImage

/**
 * Just a javafx wrapper
 *
 * @author ice1000
 * @since v1.5.0
 */
class JfxImage(image: BufferedImage) : JvmImage(image) {
	val jfxImage = WritableImage(image.width, image.height)

	init {
		SwingFXUtils.toFXImage(image, jfxImage)
	}

	override fun set(x: Int, y: Int, color: Int) {
		super.set(x, y, color)
		jfxImage.pixelWriter.setArgb(x, y, color)
	}
}
