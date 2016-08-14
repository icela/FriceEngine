package org.frice.utils.color

import java.awt.Color

/**
 * Created by ice1000 on 16-8-6.
 * referred http://blog.csdn.net/lzs109/article/details/7316507
 * @author ice1000
 */
object FBinarization {

	fun gray(rgb: Int): Int {
		val color = Color(rgb)
		return (color.blue + color.green + color.red) / 3
	}

}