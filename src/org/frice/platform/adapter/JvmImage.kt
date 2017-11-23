package org.frice.platform.adapter

import org.frice.platform.FriceImage
import org.frice.resource.graphics.ColorResource
import org.frice.utils.*
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

open class JvmImage(val image: BufferedImage) : FriceImage {
	constructor(width: Int, height: Int) : this(BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB))

	override val width = image.width
	override val height = image.height
	override operator fun get(x: Int, y: Int) = ColorResource(image.getRGB(x, y))
	override operator fun set(x: Int, y: Int, color: Int) = image.setRGB(x, y, color)

	override fun getScaledInstance(x: Double, y: Double) =
			JvmImage(run { AffineTransformOp(AffineTransform().apply { scale(x, y) }, AffineTransformOp.TYPE_BILINEAR)
					.filter(image, BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)) })

	override fun getSubImage(x: Int, y: Int, width: Int, height: Int) =
			JvmImage(image.getSubimage(x, y, width, height))

	override fun clone() = JvmImage(BufferedImage(width, height, image.type).apply {
		this@apply.data = this@JvmImage.image.data
	})

	fun greenify() = image.greenify()
	fun redify() = image.redify()
	fun bluify() = image.bluify()
}


