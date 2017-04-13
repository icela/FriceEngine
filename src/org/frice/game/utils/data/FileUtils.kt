package org.frice.game.utils.data

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Created by ice1000 on 2016/9/3.
 *
 * @author ice1000
 * @since v0.5
 */
object FileUtils {
	@JvmStatic
	fun BufferedImage.image2File(type: String, file: File) = ImageIO.write(this, type, file)

	@JvmStatic
	fun BufferedImage.image2File(file: File) = image2File("png", file)

	@JvmStatic
	fun BufferedImage.image2File(file: String) = image2File(File(file))

	@JvmStatic
	fun String.string2File(file: File) = file.writeText(this)

	@JvmStatic
	fun String.string2File(file: String) = string2File(File(file))

	@JvmStatic
	fun ByteArray.bytes2File(file: File) = file.writeBytes(this)

	@JvmStatic
	fun ByteArray.bytes2File(byteArray: ByteArray, file: String) = bytes2File(File(file))
}