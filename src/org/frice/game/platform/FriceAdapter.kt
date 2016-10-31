package org.frice.game.platform

/**
 * Created by ice1000 on 2016/10/31.
 *
 * @author ice1000
 */
interface FriceAdapter {
	val friceImage: FriceImage
	fun init(): Unit
	fun drawOval()
	fun drawString(string: String, x: Double, y: Double)
	fun drawImage(image: FriceImage, x: Double, y: Double)
	fun restore()
}

interface FriceImage {
	val width: Int
	val height: Int
}

