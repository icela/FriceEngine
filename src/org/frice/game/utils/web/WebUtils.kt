package org.frice.game.utils.web

import org.frice.game.resource.manager.ImageManager
import org.frice.game.resource.manager.URLBytesManager
import org.frice.game.resource.manager.URLTextManager
import java.net.URL
import javax.imageio.ImageIO

/**
 * Created by ice1000 on 2016/9/3.
 *
 * @author ice1000
 * @since v0.5
 */
object WebUtils {
	@Deprecated("not recommended", ReplaceWith("readText"), DeprecationLevel.WARNING)
	@JvmStatic
	fun readText(url: URL) = url.readText()

	/**
	 * reuse text already read before
	 */
	@JvmStatic
	fun readText(url: String) = URLTextManager[url]

	/**
	 * don't use this anymore
	 */
	@Deprecated("not recommended", ReplaceWith("readBytes"), DeprecationLevel.ERROR)
	@JvmStatic
	fun readBytes(url: URL) = url.readBytes()

	/**
	 * reuse bytes already read before
	 */
	@JvmStatic
	fun readBytes(url: String) = URLBytesManager[url]

	/**
	 * don't use this anymore
	 */
	@Deprecated("not recommended", ReplaceWith("readImage"), DeprecationLevel.ERROR)
	@JvmStatic
	fun readImage(url: URL) = ImageIO.read(url)!!

	/**
	 * reuse
	 */
	@JvmStatic
	fun readImage(url: String) = ImageManager[url]

	/**
	 * don't use this anymore
	 */
	@Deprecated("not recommended", ReplaceWith("readImages"), DeprecationLevel.ERROR)
	@JvmStatic
	fun readImages(url: URL) = HTMLUtils.findTag(readText(url), "img")

	/**
	 * reuse
	 */
	@JvmStatic
	fun readImages(url: String) = HTMLUtils.findTag(readText(url), "img")
}