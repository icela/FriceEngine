package org.frice.game.platform

import org.frice.game.resource.graphics.ColorResource

/**
 * Created by ice1000 on 2016/10/31.
 *
 * @author ice1000
 */
interface FriceAdapter <T> {
	val friceImage: FriceImage
	var color: ColorResource
	fun init(): Unit
	fun drawOval(x: Double , y: Double, width: Double, height: Double)
	fun drawString(string: String, x: Double, y: Double)
	fun drawImage(image: T, x: Double, y: Double)
	fun drawRect(x: Double, y: Double, width: Double, height: Double)
	fun rotate(theta: Double, x: Double, y: Double)
	fun restore()
}

interface FriceImage {
	val width: Int
	val height: Int
	operator fun get(x: Int, y: Int): ColorResource
	fun set(x: Int, y: Int, color: ColorResource)
}


interface FriceClock {
	fun init()
	fun resume()
	fun pause()
}
