package org.frice.platform.adapter

import org.frice.platform.FriceImage
import org.frice.resource.graphics.ColorResource
import org.frice.util.*
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

open class JvmImage(val image: BufferedImage) : FriceImage {
	constructor(width: Int, height: Int) : this(BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB))

	override val width = image.width
	override val height = image.height
	override operator fun get(x: Int, y: Int) = ColorResource(image.getRGB(x, y))
	override operator fun set(x: Int, y: Int, color: Int) = image.setRGB(x, y, color)

	override fun scale(x: Double, y: Double) = JvmImage(scaleImpl(x, y))
	protected fun scaleImpl(x: Double, y: Double): BufferedImage = AffineTransformOp(
		AffineTransform().apply { scale(x, y) },
		AffineTransformOp.TYPE_BILINEAR).filter(image, null)

	override fun part(x: Int, y: Int, width: Int, height: Int) = JvmImage(partImpl(x, y, width, height))
	protected fun partImpl(x: Int, y: Int, width: Int, height: Int): BufferedImage
		= image.getSubimage(x, y, width, height)

	override fun clone() = JvmImage(cloneImpl())
	protected fun cloneImpl() = BufferedImage(width, height, image.type).apply {
		this@apply.data = this@JvmImage.image.data
	}

	override fun flip(orientation: Boolean) = JvmImage(flipImpl(orientation))
	protected fun flipImpl(orientation: Boolean): BufferedImage = AffineTransformOp(
		if (orientation) AffineTransform.getScaleInstance(-1.0, 1.0).apply { translate((-width).toDouble(), 0.0) } // horizontal
		else AffineTransform.getScaleInstance(1.0, -1.0).apply { translate(0.0, (-height).toDouble()) }, // vertical
		AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(image, null)

	override fun fx() = JfxImage(image)

	fun greenify() = image.greenify()
	fun redify() = image.redify()
	fun bluify() = image.bluify()
}


