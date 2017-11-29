@file:JvmName("ColorUtils")
@file:JvmMultifileClass

package org.frice.utils

import java.awt.Color
import javafx.scene.paint.Color as JfxColor

/**
 * Created by ice1000 on 16-8-6.
 * Reference: http://blog.csdn.net/lzs109/article/details/7316507
 * @author ice1000
 * @since 0.3
 */

val asciiList = listOf('#', '0', 'X', 'x', '+', '=', '-', ';', ',', '.', ' ')

fun Color.toJfxColor(): JfxColor = JfxColor.rgb(red, green, blue)

fun Int.toAscii() = asciiList[greyify() / (256 / asciiList.size + 1)]

fun Color.average() = (blue + green + red) / 3

fun Int.greyify(): Int = Color(this).average().let { c -> makeColor(c, c, c, alpha) }

fun swing2fxColor(c: Color): JfxColor = JfxColor.rgb(c.red, c.green, c.blue)

@Suppress("NOTHING_TO_INLINE")
inline fun makeColor(red: Int, green: Int, blue: Int, alpha: Int) =
		(alpha and 255 shl 24) or (red and 255 shl 16) or (green and 255 shl 8) or (blue and 255 shl 0)

val Int.alpha: Int get() = this shr 24 and 255
val Int.red: Int get() = this shr 16 and 255
val Int.blue: Int get() = this and 255
val Int.green: Int get() = this shr 8 and 255

fun Int.greenify(): Int = makeColor(0, Color(this).average(), 0, alpha)
fun Int.redify(): Int = makeColor(Color(this).average(), 0, 0, alpha)
fun Int.bluify(): Int = makeColor(0, 0, Color(this).average(), alpha)
fun Int.darker(): Int = makeColor(red * 2 / 3, green * 2 / 3, blue * 2 / 3, alpha shl 24)
