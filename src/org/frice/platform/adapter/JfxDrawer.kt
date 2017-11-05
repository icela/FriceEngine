package org.frice.platform.adapter

import org.frice.platform.FriceDrawer
import org.frice.platform.FriceImage
import org.frice.resource.graphics.ColorResource

class JfxDrawer(val image: JfxImage) : FriceDrawer {
	override val friceImage: FriceImage
		get() = image
	override var color: ColorResource
		get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
		set(value) = Unit

	override fun init() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun drawOval(x: Double, y: Double, width: Double, height: Double) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun drawString(string: String, x: Double, y: Double) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun drawImage(image: FriceImage, x: Double, y: Double) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun drawRect(x: Double, y: Double, width: Double, height: Double) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun drawLine(x: Double, y: Double, width: Double, height: Double) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun drawRoundRect(x: Double, y: Double, width: Double, height: Double, arcWidth: Double, arcHeight: Double) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun rotate(theta: Double, x: Double, y: Double) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun rotate(theta: Double) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun restore() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}