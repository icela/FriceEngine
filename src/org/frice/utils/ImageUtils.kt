@file:JvmName("ColorUtils")
@file:JvmMultifileClass

/**
 * @author ice1000
 * @since 3.1.4
 */
package org.frice.utils

import org.frice.platform.adapter.JvmImage
import org.frice.utils.graphics.*
import java.awt.image.BufferedImage

private inline fun BufferedImage.fy(fy: BufferedImage.(Int, Int) -> Int): JvmImage {
	val jvmImage = JvmImage(width, height)
	repeat(width - 1) { x: Int ->
		repeat(height - 1) { y: Int ->
			jvmImage[x, y] = fy(x, y)
		}
	}
	return jvmImage
}

fun BufferedImage.greenify() = fy { x, y -> getRGB(x, y).greenify() }
fun BufferedImage.redify() = fy { x, y -> getRGB(x, y).redify() }
fun BufferedImage.bluify() = fy { x, y -> getRGB(x, y).bluify() }
