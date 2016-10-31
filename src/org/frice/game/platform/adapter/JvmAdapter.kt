package org.frice.game.platform.adapter

import org.frice.game.platform.FriceAdapter
import org.frice.game.platform.FriceImage
import java.awt.Frame
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

/**
 * Created by ice1000 on 2016/10/31.
 *
 * @author ice1000
 */
class JvmAdapter(val frame: Frame) : FriceAdapter {
	override fun init() {
		getG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
	}

	override val friceImage = JvmImage(frame.width, frame.height)
	val getG: Graphics2D
		get() = friceImage.image.graphics as Graphics2D

	var g: Graphics2D = getG

	override fun drawOval() {
	}

	override fun drawString(string: String, x: Double, y: Double) {
	}

	override fun drawImage(image: FriceImage, x: Double, y: Double) {
	}

	override fun restore() {
		g = getG
	}
}

class JvmImage(val image: BufferedImage) : FriceImage {
	constructor(width: Int, height: Int) : this(BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB))

	override val width = image.width
	override val height = image.height
}


