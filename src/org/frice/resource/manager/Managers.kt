/**
 * platform dependent
 * Java SE only
 * Created by ice1000 on 2016/10/24.
 *
 * @author ice1000
 */
package org.frice.resource.manager

import org.frice.platform.FriceImage
import org.frice.platform.adapter.JvmImage
import java.io.File
import java.net.URL
import java.nio.charset.Charset
import java.util.*
import javax.imageio.ImageIO

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

	operator fun get(path: String): T = res[path] ?: create(path).apply {
		res += Pair(path, this)
	}

	operator fun set(path: String, new: T) {
		res[path] = new
	}

	companion object Utils {
		fun clearAll() {
			org.frice.resource.manager.FileBytesManager.res.clear()
			org.frice.resource.manager.FileTextManager.res.clear()
			org.frice.resource.manager.ImageManager.res.clear()
			org.frice.resource.manager.WebImageManager.res.clear()
			org.frice.resource.manager.URLBytesManager.res.clear()
			org.frice.resource.manager.URLTextManager.res.clear()
		}
	}
}

/**
 * @author ice1000
 * @since v1.1
 */
object FileTextManager : org.frice.resource.manager.FManager<String> {
	override val res = HashMap<String, String>()
	override fun create(path: String) = File(path).readText()
}

/**
 * @author ice1000
 * @since v1.1
 */
object FileBytesManager : org.frice.resource.manager.FManager<ByteArray> {
	override val res = HashMap<String, ByteArray>()
	override fun create(path: String) = File(path).readBytes()
}

/**
 * @author ice1000
 * @since v0.6
 */
object ImageManager : org.frice.resource.manager.FManager<org.frice.platform.FriceImage> {
	override val res = HashMap<String, org.frice.platform.FriceImage>()
	override fun create(path: String) = org.frice.platform.adapter.JvmImage(ImageIO.read(File(path))!!)
	override operator fun get(path: String) = super.get(path).clone()
}

/**
 * @author ice1000
 * @since v0.6
 */
object WebImageManager : org.frice.resource.manager.FManager<org.frice.platform.FriceImage> {
	override val res = HashMap<String, org.frice.platform.FriceImage>()
	override fun create(path: String) = org.frice.platform.adapter.JvmImage(ImageIO.read(URL(path))!!)
	override operator fun get(path: String) = super.get(path).clone()
}

/**
 * @author ice1000
 * @since v0.6
 */
object URLTextManager : org.frice.resource.manager.FManager<String> {
	override val res = HashMap<String, String>()
	override fun create(path: String) = URL(path).readText(Charset.defaultCharset())
}

/**
 * @author ice1000
 * @since v0.6
 */
object URLBytesManager : org.frice.resource.manager.FManager<ByteArray> {
	override val res = HashMap<String, ByteArray>()
	override fun create(path: String) = URL(path).readBytes()
}
