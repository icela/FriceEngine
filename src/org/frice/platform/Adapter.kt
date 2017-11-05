package org.frice.platform

/**
 * Created by ice1000 on 2016/10/31.
 *
 * @author ice1000
 */
interface FriceDrawer {
	val friceImage: org.frice.platform.FriceImage
	var color: org.frice.resource.graphics.ColorResource
	fun init()
	fun drawOval(x: Double, y: Double, width: Double, height: Double)
	fun drawString(string: String, x: Double, y: Double)
	fun drawImage(image: org.frice.platform.FriceImage, x: Double, y: Double)
	fun drawRect(x: Double, y: Double, width: Double, height: Double)
	fun drawLine(x: Double, y: Double, width: Double, height: Double)
	fun drawRoundRect(x: Double, y: Double, width: Double, height: Double, arcWidth: Double, arcHeight: Double)
	fun rotate(theta: Double, x: Double, y: Double)
	fun rotate(theta: Double)
	fun restore()
}

interface FriceImage {
	val width: Int
	val height: Int
	operator fun get(x: Int, y: Int): org.frice.resource.graphics.ColorResource
	operator fun set(x: Int, y: Int, color: org.frice.resource.graphics.ColorResource) = set(x, y, color.color.rgb)
	operator fun set(x: Int, y: Int, color: Int)
	fun getScaledInstance(x: Double, y: Double): org.frice.platform.FriceImage
	fun getSubImage(x: Int, y: Int, width: Int, height: Int): org.frice.platform.adapter.JvmImage

	/**
	 * copy a image.
	 * to replace the operation of reading an image from file.
	 * if an image is already read from file, it can be copied from RAM directly.
	 *
	 * classes in this file is to do this job.
	 */
	fun clone(): org.frice.platform.FriceImage
}

interface FriceClock {
	val current: Long
	fun init()
	fun resume()
	fun pause()
}
