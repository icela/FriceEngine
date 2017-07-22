/**
 * Created by ice1000 on 2016/9/3.
 *
 * @author ice1000
 * @since v0.5
 */
@file:JvmName("FileUtils")

package org.frice.game.utils.data

import org.frice.game.platform.adapter.JvmImage
import org.frice.game.resource.manager.FileBytesManager
import org.frice.game.resource.manager.FileTextManager
import org.frice.game.resource.manager.ImageManager
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun BufferedImage.image2File(type: String, file: File) = JvmImage(this).image2File(type, file)

fun JvmImage.image2File(type: String, file: File) {
	ImageIO.write(image, type, file)
	ImageManager[file.path] = this
}

fun BufferedImage.image2File(file: File) = image2File("png", file)
fun JvmImage.image2File(file: File) = image2File("png", file)

fun BufferedImage.image2File(file: String) = image2File(File(file))
fun JvmImage.image2File(file: String) = image2File(File(file))

fun String.string2File(file: File) {
	file.writeText(this)
	FileTextManager[file.path] = this
}

fun String.string2File(file: String) = string2File(File(file))

fun ByteArray.bytes2File(file: File) {
	file.writeBytes(this)
	FileBytesManager[file.path] = this
}

fun ByteArray.bytes2File(byteArray: ByteArray, file: String) = bytes2File(File(file))

fun File.readString() = FileTextManager[path]

fun String.file2String() = FileTextManager[this]

fun File.readByteArray() = FileBytesManager[path]

fun String.file2ByteArray() = FileBytesManager[this]
