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
	override var color: ColorResource
		get() = TODO("not implemented")
		set(value) = Unit

	fun fromColor(c: Color): JfxColor = JfxColor.rgb(c.red, c.green, c.blue)

	override fun init() {
		forceRun { g.font = Font("Consolas", 16.0) }
		TODO("not implemented")
	}

	override fun drawOval(x: Double, y: Double, width: Double, height: Double) {
		TODO("not implemented")
	}

	override fun drawString(string: String, x: Double, y: Double) {
		TODO("not implemented")
	}

	override fun drawImage(image: FriceImage, x: Double, y: Double) {
		TODO("not implemented")
	}

	override fun drawRect(x: Double, y: Double, width: Double, height: Double) {
		TODO("not implemented")
	}

	override fun drawLine(x: Double, y: Double, width: Double, height: Double) {
		TODO("not implemented")
	}

	override fun drawRoundRect(x: Double, y: Double, width: Double, height: Double, arcWidth: Double, arcHeight: Double) {
		TODO("not implemented")
	}

	override fun rotate(theta: Double, x: Double, y: Double) {
		TODO("not implemented")
	}

	override fun rotate(theta: Double) {
		TODO("not implemented")
	}

	override fun restore() = g.restore()
}