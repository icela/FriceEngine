package org.frice.game.utils.graphics.utils

import java.awt.Color

/**
 * Created by ice1000 on 16-8-6.
 * referred http://blog.csdn.net/lzs109/article/details/7316507
 * @author ice1000
 */

fun Int.gray(): Int {
	val color = Color(this)
	return (color.blue + color.green + color.red) / 3
}

fun Int.darker(): Int {
	val color = Color(this)
	return (color.blue * 2 / 3) or ((color.green * 2 / 3) shl 8) or
			((color.red * 2 / 3) shl 16) or ((color.alpha shl 24))
}