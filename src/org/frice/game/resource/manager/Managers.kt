package org.frice.game.resource.manager

import org.frice.game.utils.web.WebUtils
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import java.nio.charset.Charset
import java.util.*
import javax.imageio.ImageIO

/**
 * Created by ice1000 on 2016/10/24.
 *
 * @author ice1000
 */

fun BufferedImage.clone(): BufferedImage {
	return BufferedImage(width, height, type).apply {
		setRGB(0, 0, width, height,
				getRGB(0, 0, width, height, null, 0, 0),
				0, 0)
	}
}

interface FManager<T> {
	val res: MutableMap<String, T>
	fun create(path: String): T

	operator fun get(path: String): T {
		if (res.containsKey(path)) return res[path]!!
		else return create(path).apply {
			res += Pair(path, this)
		}
	}
}

object FileManager : FManager<File> {
	override val res = HashMap<String, File>()
	override fun create(path: String) = File(path)
}

object ImageManager : FManager<BufferedImage> {
	override val res = HashMap<String, BufferedImage>()
	override fun create(path: String) = ImageIO.read(File(path))!!
	override fun get(path: String) = super.get(path).clone()
}

object WebImageManager : FManager<BufferedImage> {
	override val res = HashMap<String, BufferedImage>()
	override fun create(path: String) = WebUtils.readImage(path)
	override fun get(path: String) = super.get(path).clone()
}

object URLTextManager : FManager<String> {
	override val res = HashMap<String, String>()
	override fun create(path: String) = URL(path).readText(Charset.defaultCharset())
}

object URLBytesManager : FManager<ByteArray> {
	override val res = HashMap<String, ByteArray>()
	override fun create(path: String) = URL(path).readBytes()
}


