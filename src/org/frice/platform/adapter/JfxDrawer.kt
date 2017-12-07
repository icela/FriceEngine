package org.frice.platform.adapter

import javafx.scene.canvas.GraphicsContext
import javafx.scene.text.Font
import org.frice.obj.button.FText
import org.frice.platform.FriceDrawer
import org.frice.platform.FriceImage
import org.frice.resource.graphics.ColorResource
import org.frice.utils.forceRun
import org.frice.utils.swing2fxColor

/**
 * A drawer implementation for javafx
 *
 * @author ice1000
 * @since v1.5.0
 */
class JfxDrawer(val g: GraphicsContext) : FriceDrawer {
	override var color = ColorResource.BLUE
		set(value) {
			field = value
			g.fill = swing2fxColor(field.color)
		}

	override fun stringSize(size: Double) {
		g.font = Font(g.font.name, size)
	}

	override fun useFont(text: FText) {
		if (text.`font tmp obj` == null) text.`font tmp obj` = Font(text.fontName, text.textSize)
		if (g.font != text.`font tmp obj`)
			g.font = text.`font tmp obj` as Font?
	}

	override fun init() {
		forceRun { g.font = Font(16.0) }
	}

	override fun drawOval(x: Double, y: Double, width: Double, height: Double) {
		g.fillOval(x, y, width, height)
	}

	override fun strokeOval(x: Double, y: Double, width: Double, height: Double) {
		g.strokeOval(x, y, width, height)
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

	override fun strokeRect(x: Double, y: Double, width: Double, height: Double) {
		g.fillRect(x, y, width, height)
	}

	override fun drawLine(x: Double, y: Double, width: Double, height: Double) {
		g.strokeLine(x, y, width, height)
	}

	override fun drawRoundRect(x: Double, y: Double, width: Double, height: Double, arcWidth: Double, arcHeight: Double) {
		g.fillRoundRect(x, y, width, height, arcWidth, arcHeight)
	}

	override fun strokeRoundRect(x: Double, y: Double, width: Double, height: Double, arcWidth: Double, arcHeight: Double) {
		g.strokeRoundRect(x, y, width, height, arcWidth, arcHeight)
	}

	override fun rotate(theta: Double) {
		g.rotate(theta)
	}

	override fun restore() = g.restore()
}