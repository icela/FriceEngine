/**
 * Created by ice1000 on 2016/9/3.
 *
 * @author ice1000
 * @since v0.5
 */
@file:JvmName("WebUtils")
package org.frice.game.utils.web

import org.frice.game.resource.manager.ImageManager
import org.frice.game.resource.manager.URLBytesManager
import org.frice.game.resource.manager.URLTextManager

/**
 * reuse text already read before
 */
fun readText(url: String) = URLTextManager[url]

/**
 * reuse bytes already read before
 */
fun readBytes(url: String) = URLBytesManager[url]

/**
 * reuse
 */
fun readImage(url: String) = ImageManager[url]

/**
 * reuse
 */
fun readImages(url: String) = findTag(readText(url), "img")
