package org.frice.platform.adapter

import javafx.scene.canvas.GraphicsContext
import javafx.scene.text.Font
import org.frice.platform.FriceDrawer
import org.frice.platform.FriceImage
import org.frice.resource.graphics.ColorResource
import org.frice.utils.misc.forceRun
import java.awt.Color
import javafx.scene.paint.Color as JfxColor

class JfxDrawer(val g: GraphicsContext) : FriceDrawer {
	override var color = ColorResource.BLUE
		set(value) {
			field = value
			g.fill = fromColor(field.color)
		}

	fun fromColor(c: Color): JfxColor = JfxColor.rgb(c.red, c.green, c.blue)

	override fun init() {
		forceRun { g.font = Font("Consolas", 16.0) }
	}

	override fun drawOval(x: Double, y: Double, width: Double, height: Double) {
		g.fillOval(x, y, width, height)
	}

	override fun drawString(string: String, x: Double, y: Double) {
		g.fillText(string, x, y)
	}

	override fun drawImage(image: FriceImage, x: Double, y: Double) {
		g.drawImage((image as JfxImage).jfxImage, x, y)
	}

	override fun drawRect(x: Double, y: Double, width: Double, height: Double) {
		g.fillRect(x, y, width, height)
	}

	override fun drawLine(x: Double, y: Double, width: Double, height: Double) {
		g.strokeLine(x, y, width, height)
	}

	override fun drawRoundRect(x: Double, y: Double, width: Double, height: Double, arcWidth: Double, arcHeight: Double) {
		g.fillRoundRect(x, y, width, height, arcWidth, arcHeight)
	}

	override fun rotate(theta: Double) {
		g.rotate(theta)
	}

	override fun restore() = g.restore()
}