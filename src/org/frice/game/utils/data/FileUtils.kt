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
	@JvmStatic fun image2File(image: BufferedImage, file: File) = ImageIO.write(image, "png", file)
	@JvmStatic fun image2File(image: BufferedImage, file: String) = image2File(image, File(file))

	@JvmStatic fun string2File(string: String, file: File) = file.writeText(string)
	@JvmStatic fun string2File(string: String, file: String) = string2File(string, File(file))

	@JvmStatic fun bytes2File(byteArray: ByteArray, file: File) = file.writeBytes(byteArray)
	@JvmStatic fun bytes2File(byteArray: ByteArray, file: String) = bytes2File(byteArray, File(file))
}