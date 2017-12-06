package org.frice.platform.adapter

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.WritableImage
import java.awt.image.BufferedImage

/**
 * Just a javafx wrapper
 *
 * @author ice1000
 * @since v1.5.0
 */
class JfxImage : JvmImage {
	constructor(width: Int, height: Int) : super(BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB))

	constructor(image: BufferedImage) : super(image)

	val jfxImage: WritableImage

	init {
		this.jfxImage = WritableImage(image.width, image.height)
		SwingFXUtils.toFXImage(image, jfxImage)
	}

	override fun set(x: Int, y: Int, color: Int) {
		super.set(x, y, color)
		jfxImage.pixelWriter.setArgb(x, y, color)
	}
}
