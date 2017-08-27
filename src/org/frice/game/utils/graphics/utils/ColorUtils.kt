@file:JvmName("ColorUtils")
@file:JvmMultifileClass

package org.frice.game.utils.graphics.utils

import java.awt.Color

/**
 * Created by ice1000 on 16-8-6.
 * Reference: http://blog.csdn.net/lzs109/article/details/7316507
 * @author ice1000
 * @since 0.3
 */

val asciiList = listOf('#', '0', 'X', 'x', '+', '=', '-', ';', ',', '.', ' ')

fun Int.toAscii() = asciiList[grayify() / (256 / asciiList.size + 1)]

fun Color.average() = (blue + green + red) / 3

fun Int.grayify(): Int = Color(this).average().let { c -> makeColor(c, c, c, alpha) }

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
