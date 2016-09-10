package org.frice.game.utils.graphics.utils

import java.awt.Color

/**
 * Created by ice1000 on 16-8-6.
 * Reference: http://blog.csdn.net/lzs109/article/details/7316507
 * @author ice1000
 */

object ColorUtils {
	@JvmField val asciiList = listOf('#', '0', 'X', 'x', '+', '=', '-', ';', ',', '.', ' ')

	@JvmStatic fun Int.toAscii() = asciiList[gray() / (256 / asciiList.size + 1)]

	@JvmStatic fun Int.gray(): Int {
		val color = Color(this)
		return (color.blue + color.green + color.red) / 3
	}

	@JvmStatic fun Int.darker(): Int {
		val color = Color(this)
		return (color.blue * 2 / 3) or ((color.green * 2 / 3) shl 8) or
				((color.red * 2 / 3) shl 16) or ((color.alpha shl 24))
	}

	@JvmStatic fun darkerRGB(int: Int) = int.darker()
}