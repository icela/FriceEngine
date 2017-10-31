/**
 * Created by ice1000 on 2016/9/3.
 *
 * @author ice1000
 * @since v0.5
 */
@file:JvmName("FileUtils")

package org.frice.utils.data

import org.frice.platform.adapter.JvmImage
import org.frice.resource.manager.FileBytesManager
import org.frice.resource.manager.FileTextManager
import org.frice.resource.manager.ImageManager
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun BufferedImage.image2File(type: String, file: File) = org.frice.platform.adapter.JvmImage(this).image2File(type, file)

fun org.frice.platform.adapter.JvmImage.image2File(type: String, file: File) {
	ImageIO.write(image, type, file)
	org.frice.resource.manager.ImageManager[file.path] = this
}

fun BufferedImage.image2File(file: File) = image2File("png", file)
fun org.frice.platform.adapter.JvmImage.image2File(file: File) = image2File("png", file)

fun BufferedImage.image2File(file: String) = image2File(File(file))
fun org.frice.platform.adapter.JvmImage.image2File(file: String) = image2File(File(file))

fun String.string2File(file: File) {
	file.writeText(this)
	org.frice.resource.manager.FileTextManager[file.path] = this
}

fun String.string2File(file: String) = string2File(File(file))

fun ByteArray.bytes2File(file: File) {
	file.writeBytes(this)
	org.frice.resource.manager.FileBytesManager[file.path] = this
}

fun ByteArray.bytes2File(file: String) = bytes2File(File(file))

fun File.readString() = org.frice.resource.manager.FileTextManager[path]

fun String.file2String() = org.frice.resource.manager.FileTextManager[this]

fun File.readByteArray() = org.frice.resource.manager.FileBytesManager[path]

fun String.file2ByteArray() = org.frice.resource.manager.FileBytesManager[this]
