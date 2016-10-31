package org.frice.game.platform.adapter

import org.frice.game.platform.FriceAdapter
import org.frice.game.platform.FriceImage
import org.frice.game.resource.graphics.ColorResource
import java.awt.Frame
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

/**
 * Created by ice1000 on 2016/10/31.
 *
 * @author ice1000
 */
data class JvmAdapter(val frame: Frame) : FriceAdapter<BufferedImage> {
	override fun init() {
		getG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
	}

	override val friceImage = JvmImage(frame.width, frame.height)
	override var color: ColorResource
		get() = ColorResource(g.color)
		set(value) {
			g.color = value.color
		}

	val getG: Graphics2D
		get() = friceImage.image.graphics as Graphics2D

	var g: Graphics2D = getG

	override fun drawOval(x: Double, y: Double, width: Double, height: Double) =
			g.drawOval(x.toInt(), y.toInt(), width.toInt(), height.toInt())

	override fun drawString(string: String, x: Double, y: Double) =
			g.drawString(string, x.toInt(), y.toInt())

	override fun drawImage(image: BufferedImage, x: Double, y: Double) {
		g.drawImage(image, x.toInt(), y.toInt(), frame)
	}

	override fun drawRect(x: Double, y: Double, width: Double, height: Double) =
			g.drawRect(x.toInt(), y.toInt(), width.toInt(), height.toInt())

	override fun rotate(theta: Double, x: Double, y: Double) = g.rotate(theta, x, y)

	override fun restore() {
		g = getG
	}
}

data class JvmImage(val image: BufferedImage) : FriceImage {
	constructor(width: Int, height: Int) : this(BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB))

	override val width = image.width
	override val height = image.height
	override fun get(x: Int, y: Int) = ColorResource(image.getRGB(x, y))
	override fun set(x: Int, y: Int, color: ColorResource) {
		image.setRGB(x, y, color.color.rgb)
	}
}


