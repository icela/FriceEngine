package org.frice.platform.adapter

import javafx.scene.canvas.GraphicsContext
import org.frice.platform.FriceDrawer
import org.frice.platform.FriceImage
import org.frice.resource.graphics.ColorResource

class JfxDrawer(val g: GraphicsContext) : FriceDrawer {
	override val friceImage: FriceImage
		get() = TODO()
	override var color: ColorResource
		get() = TODO("not implemented")
		set(value) = Unit

	override fun init() {
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

	override fun restore() {
		TODO("not implemented")
	}
}