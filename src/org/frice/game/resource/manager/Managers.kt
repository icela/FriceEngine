/**
 * Created by ice1000 on 2016/10/24.
 *
 * @author ice1000
 */
package org.frice.game.resource.manager

import org.frice.game.utils.web.WebUtils
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import java.nio.charset.Charset
import java.util.*
import javax.imageio.ImageIO

/**
 * copy a image.
 * to replace the operation of reading an image from file.
 * if an image is already read from file, it can be copied from RAM directly.
 *
 * classes in this file is to do this job.
 */
internal fun BufferedImage.clone(): BufferedImage {
	return BufferedImage(width, height, type).apply {
		setRGB(0, 0, width, height,
				getRGB(0, 0, width, height, null, 0, 0),
				0, 0)
	}
}

/**
 * resource manager, like a pool.
 * to do the resource reusing.
 *
 * @param T the type of the resource.
 * @property res the resource map(a string key and a T value)
 * @author ice1000
 * @since v0.6
 */
internal interface FManager<T> {
	val res: MutableMap<String, T>
	fun create(path: String): T

	operator fun get(path: String): T {
		if (res.containsKey(path)) return res[path]!!
		else return create(path).apply {
			res += Pair(path, this)
		}
	}
}

/**
 * @author ice1000
 * @since v0.6
 */
object FileManager : FManager<File> {
	override val res = HashMap<String, File>()
	override fun create(path: String) = File(path)
}

/**
 * @author ice1000
 * @since v0.6
 */
object ImageManager : FManager<BufferedImage> {
	override val res = HashMap<String, BufferedImage>()
	override fun create(path: String) = ImageIO.read(File(path))!!
	override fun get(path: String) = super.get(path).clone()
}

/**
 * @author ice1000
 * @since v0.6
 */
object WebImageManager : FManager<BufferedImage> {
	override val res = HashMap<String, BufferedImage>()
	override fun create(path: String) = WebUtils.readImage(path)
	override fun get(path: String) = super.get(path).clone()
}

/**
 * @author ice1000
 * @since v0.6
 */
object URLTextManager : FManager<String> {
	override val res = HashMap<String, String>()
	override fun create(path: String) = URL(path).readText(Charset.defaultCharset())
}

/**
 * @author ice1000
 * @since v0.6
 */
object URLBytesManager : FManager<ByteArray> {
	override val res = HashMap<String, ByteArray>()
	override fun create(path: String) = URL(path).readBytes()
}


