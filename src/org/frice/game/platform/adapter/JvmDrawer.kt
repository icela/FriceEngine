package org.frice.game.platform.adapter

import org.frice.game.platform.FriceDrawer
import org.frice.game.platform.FriceImage
import org.frice.game.resource.graphics.ColorResource
import java.awt.Frame
import java.awt.Graphics2D
import java.awt.RenderingHints

/**
 * Created by ice1000 on 2016/10/31.
 *
 * @author ice1000
 */
class JvmDrawer(val frame: Frame) : FriceDrawer {

	override fun init() {
		getG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
	}

	override val friceImage = JvmImage(frame.width, frame.height)
	override var color: ColorResource
		get() = ColorResource(g.color)
		set(value) {
			g.color = value.color
		}

	val getG: Graphics2D get() = friceImage.image.graphics as Graphics2D

	var g: Graphics2D = getG

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
		g = getG
	}
}