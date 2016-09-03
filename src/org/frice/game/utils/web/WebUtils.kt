package org.frice.game.utils.web

import java.net.URL
import javax.imageio.ImageIO

/**
 * Created by ice1000 on 2016/9/3.
 *
 * @author ice1000
 * @since v0.5
 */
object WebUtils {
	@JvmStatic fun readText(url: URL) = url.readText()
	@JvmStatic fun readText(url: String) = readText(URL(url))

	@JvmStatic fun readBytes(url: URL) = url.readBytes()
	@JvmStatic fun readBytes(url: String) = readBytes(URL(url))

	@JvmStatic fun readImage(url: URL) = ImageIO.read(url)!!
	@JvmStatic fun readImage(url: String) = readImage(URL(url))

	@JvmStatic fun readImages(url: String) = HTMLUtils.findTag(readText(url), "img")
	@JvmStatic fun readImages(url: URL) = HTMLUtils.findTag(readText(url), "img")
}