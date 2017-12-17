package org.frice.platform

import org.frice.platform.adapter.JfxImage
import org.frice.resource.graphics.ColorResource

interface FriceImage {
	val width: Int
	val height: Int
	operator fun get(x: Int, y: Int): ColorResource
	operator fun set(x: Int, y: Int, color: ColorResource) = set(x, y, color.color)
	operator fun set(x: Int, y: Int, color: Int)
	fun getScaledInstance(x: Double, y: Double): FriceImage
	fun getSubImage(x: Int, y: Int, width: Int, height: Int): FriceImage
	fun fx(): JfxImage

	/**
	 * copy a image.
	 * to replace the operation of reading an image from file.
	 * if an image is already read from file, it can be copied from RAM directly.
	 *
	 * classes in this file is to do this job.
	 */
	fun clone(): FriceImage
}