@file:JvmName("ColorUtils")
@file:JvmMultifileClass

/**
 * @author ice1000
 * @since 3.1.4
 */
package org.frice.game.utils.graphics.utils

import org.frice.game.platform.adapter.JvmImage
import java.awt.image.BufferedImage

private inline fun BufferedImage.fy(fy: BufferedImage.(Int, Int) -> Int): JvmImage {
	val jvmImage = JvmImage(this)
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
