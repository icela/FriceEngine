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
	/**
	 * reuse text already read before
	 */
	@JvmStatic
	fun readText(url: String) = URLTextManager[url]

	/**
	 * reuse bytes already read before
	 */
	@JvmStatic
	fun readBytes(url: String) = URLBytesManager[url]

	/**
	 * reuse
	 */
	@JvmStatic
	fun readImage(url: String) = ImageManager[url]

	/**
	 * reuse
	 */
	@JvmStatic
	fun readImages(url: String) = HTMLUtils.findTag(readText(url), "img")
}