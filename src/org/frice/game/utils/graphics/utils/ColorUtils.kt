@file:JvmName("ColorUtils")

package org.frice.game.utils.graphics.utils

import java.awt.Color

/**
 * Created by ice1000 on 16-8-6.
 * Reference: http://blog.csdn.net/lzs109/article/details/7316507
 * @author ice1000
 */

val asciiList = listOf('#', '0', 'X', 'x', '+', '=', '-', ';', ',', '.', ' ')

fun Int.toAscii() = asciiList[gray() / (256 / asciiList.size + 1)]

fun Int.gray(): Int {
	val color = Color(this)
	val c = (color.blue + color.green + color.red) / 3
	return Color(c, c, c).rgb
}

fun Int.darker(): Int {
	val color = Color(this)
	return (color.blue * 2 / 3) or ((color.green * 2 / 3) shl 8) or
			((color.red * 2 / 3) shl 16) or ((color.alpha shl 24))
}

fun darkerRGB(int: Int) = int.darker()