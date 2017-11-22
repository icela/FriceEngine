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
interface FManager<T> {
	val res: MutableMap<String, T>
	fun create(path: String): T

	operator fun get(path: String): T = if (enabled) res[path] ?: create(path).apply {
		res += Pair(path, this)
	} else create(path)

	operator fun set(path: String, new: T) {
		res[path] = new
	}

	companion object Utils {
		var enabled = true
			set(value) {
				field = value
				clearAll()
			}

		fun clearAll() {
			FileBytesManager.res.clear()
			FileTextManager.res.clear()
			ImageManager.res.clear()
			WebImageManager.res.clear()
			URLBytesManager.res.clear()
			URLTextManager.res.clear()
		}
	}
}

/**
 * @author ice1000
 * @since v1.1
 */
object FileTextManager : FManager<String> {
	override val res = HashMap<String, String>()
	override fun create(path: String) = File(path).readText()
}

/**
 * @author ice1000
 * @since v1.1
 */
object FileBytesManager : FManager<ByteArray> {
	override val res = HashMap<String, ByteArray>()
	override fun create(path: String) = File(path).readBytes()
}

/**
 * @author ice1000
 * @since v0.6
 */
object ImageManager : FManager<FriceImage> {
	override val res = HashMap<String, FriceImage>()
	override fun create(path: String) = JvmImage(ImageIO.read(File(path)))
	override operator fun get(path: String) = super.get(path).clone()
}

/**
 * @author ice1000
 * @since v0.6
 */
object WebImageManager : FManager<FriceImage> {
	override val res = HashMap<String, FriceImage>()
	override fun create(path: String) = JvmImage(ImageIO.read(URL(path)))
	override operator fun get(path: String) = super.get(path).clone()
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
