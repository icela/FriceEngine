package org.frice.platform.adapter

import org.frice.platform.FriceDrawer
import org.frice.platform.FriceImage
import org.frice.resource.graphics.ColorResource
import java.awt.*

/**
 * Created by ice1000 on 2016/10/31.
 *
 * @author ice1000
 */
class JvmDrawer(private val frame: Frame) : FriceDrawer {

	override fun init() {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
		g.font = Font("Consolas", Font.PLAIN, 16)
	}

	val friceImage = JvmImage(frame.width, frame.height)
	override var color: ColorResource
		get() = ColorResource(g.color)
		set(value) {
			g.color = value.color
		}

	var g: Graphics2D = friceImage.image.graphics as Graphics2D

	fun setStringSize(size: Int) {
		g.font = g.font.deriveFont(size.toFloat())
	}

	override fun drawOval(x: Double, y: Double, width: Double, height: Double) =
			g.fillOval(x.toInt(), y.toInt(), width.toInt(), height.toInt())

	override fun drawString(string: String, x: Double, y: Double) =
			g.drawString(string, x.toInt(), y.toInt())

	override fun drawImage(image: FriceImage, x: Double, y: Double) {
		g.drawImage((image as JvmImage).image, x.toInt(), y.toInt(), frame)
	}

	override fun drawRect(x: Double, y: Double, width: Double, height: Double) =
			g.fillRect(x.toInt(), y.toInt(), width.toInt(), height.toInt())

	override fun drawLine(x: Double, y: Double, width: Double, height: Double) =
			g.drawLine(x.toInt(), y.toInt(), width.toInt(), height.toInt())

	override fun rotate(theta: Double, x: Double, y: Double) = g.rotate(theta, x, y)
	override fun rotate(theta: Double) = g.rotate(theta)

	override fun drawRoundRect(
			x: Double,
			y: Double,
			width: Double,
			height: Double,
			arcWidth: Double,
			arcHeight: Double) =
			g.fillRoundRect(
					x.toInt(),
					y.toInt(),
					width.toInt(),
					height.toInt(),
					arcWidth.toInt(),
					arcHeight.toInt()
			)

	override fun restore() {
		g = friceImage.image.graphics as Graphics2D
	}
}