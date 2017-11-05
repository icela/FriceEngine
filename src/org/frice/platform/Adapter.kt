package org.frice.platform

import org.frice.platform.adapter.JvmImage
import org.frice.resource.graphics.ColorResource

/**
 * Created by ice1000 on 2016/10/31.
 *
 * @author ice1000
 */
interface FriceDrawer {
	val friceImage: FriceImage
	var color: ColorResource
	fun init()
	fun drawOval(x: Double, y: Double, width: Double, height: Double)
	fun drawString(string: String, x: Double, y: Double)
	fun drawImage(image: FriceImage, x: Double, y: Double)
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
	operator fun get(x: Int, y: Int): ColorResource
	operator fun set(x: Int, y: Int, color: ColorResource) = set(x, y, color.color.rgb)
	operator fun set(x: Int, y: Int, color: Int)
	fun getScaledInstance(x: Double, y: Double): FriceImage
	fun getSubImage(x: Int, y: Int, width: Int, height: Int): FriceImage

	/**
	 * copy a image.
	 * to replace the operation of reading an image from file.
	 * if an image is already read from file, it can be copied from RAM directly.
	 *
	 * classes in this file is to do this job.
	 */
	fun clone(): FriceImage
}
