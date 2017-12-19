@file:Suppress("NOTHING_TO_INLINE")
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

inline fun Color.toJfxColor(): JfxColor = JfxColor.rgb(red, green, blue, alpha / 256.0)
inline fun Int.toJfxColor(): JfxColor = JfxColor.rgb(red, green, blue, alpha / 256.0)
inline fun Int.toAscii() = asciiList[greyify() / (256 / asciiList.size + 1)]
inline fun Color.average() = (blue + green + red) / 3
inline fun Int.greyify(): Int = average.let { c -> makeColor(c, c, c, alpha) }

inline fun makeColor(color: Color) = makeColor(color.rgb, color.alpha)
inline fun makeColor(rgb: Int, alpha: Int) = makeColor(rgb.red, rgb.green, rgb.blue, alpha)
inline fun makeColor(red: Int, green: Int, blue: Int, alpha: Int) =
	(alpha and 255 shl 24) or (red and 255 shl 16) or (green and 255 shl 8) or (blue and 255 shl 0)

inline val Int.alpha: Int inline get() = this shr 24 and 255
inline val Int.red: Int inline get() = this shr 16 and 255
inline val Int.blue: Int inline get() = this and 255
inline val Int.green: Int inline get() = this shr 8 and 255
inline val Int.average inline get() = (blue + green + red) / 3

inline fun Int.greenify(): Int = makeColor(0, average, 0, alpha)
inline fun Int.redify(): Int = makeColor(average, 0, 0, alpha)
inline fun Int.bluify(): Int = makeColor(0, 0, average, alpha)
