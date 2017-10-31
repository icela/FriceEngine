package org.frice.platform.adapter

import org.frice.platform.FriceImage
import org.frice.resource.graphics.ColorResource
import org.frice.utils.graphics.utils.*
import java.awt.image.BufferedImage

class JvmImage(val image: BufferedImage) : org.frice.platform.FriceImage {
	constructor(width: Int, height: Int) : this(BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB))

	override val width = image.width
	override val height = image.height
	override operator fun get(x: Int, y: Int) = org.frice.resource.graphics.ColorResource(image.getRGB(x, y))
	override operator fun set(x: Int, y: Int, color: Int) = image.setRGB(x, y, color)

	override fun getScaledInstance(x: Double, y: Double) =
			org.frice.platform.adapter.JvmImage(image.getScaledInstance(x.toInt(), y.toInt(), BufferedImage.SCALE_DEFAULT) as BufferedImage)

	override fun getSubImage(x: Int, y: Int, width: Int, height: Int) =
			org.frice.platform.adapter.JvmImage(image.getSubimage(x, y, width, height))

	override fun clone(): org.frice.platform.FriceImage {
		return org.frice.platform.adapter.JvmImage(BufferedImage(width, height, image.type).apply {
			this@apply.data = this@JvmImage.image.data
		})
	}

	fun greenify() = image.greenify()
	fun redify() = image.redify()
	fun bluify() = image.bluify()
}


