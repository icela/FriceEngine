package org.frice.platform

import org.frice.platform.adapter.JfxImage
import org.frice.resource.graphics.ColorResource

/**
 * @author ice1000
 * @since v1.5.0
 */
interface FriceImage {
	val width: Int
	val height: Int
	operator fun get(x: Int, y: Int): ColorResource
	operator fun set(x: Int, y: Int, color: ColorResource) = set(x, y, color.color)
	operator fun set(x: Int, y: Int, color: Int)

	/**
	 * convert this into a JfxImage
	 * @return the corresponding JfxImage
	 */
	fun fx(): JfxImage

	/**
	 * @param x size proportion on x axis
	 * @param y size proportion on y axis
	 * @return scale image
	 */
	fun scale(x: Double, y: Double): FriceImage

	/**
	 * @param x location x
	 * @param y location y
	 * @param width width
	 * @param height height
	 * @return a sub image
	 */
	fun part(x: Int, y: Int, width: Int, height: Int): FriceImage

	/**
	 * @since v1.7.11
	 * @param orientation true: horizontal; false: vertical
	 * @return flipped image
	 */
	fun flip(orientation: Boolean): FriceImage

	/**
	 * copy a image.
	 * to replace the operation of reading an image from file.
	 * if an image is already read from file, it can be copied from RAM directly.
	 *
	 * classes in this file is to do this job.
	 * @return A copy of this image
	 */
	fun clone(): FriceImage
}