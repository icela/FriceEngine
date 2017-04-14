/**
 * Created by ice1000 on 2016/9/3.
 *
 * @author ice1000
 * @since v0.5
 */
package org.frice.game.utils.data

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun BufferedImage.image2File(type: String, file: File) = ImageIO.write(this, type, file)

fun BufferedImage.image2File(file: File) = image2File("png", file)

fun BufferedImage.image2File(file: String) = image2File(File(file))

fun String.string2File(file: File) = file.writeText(this)

fun String.string2File(file: String) = string2File(File(file))

fun ByteArray.bytes2File(file: File) = file.writeBytes(this)

fun ByteArray.bytes2File(byteArray: ByteArray, file: String) = bytes2File(File(file))