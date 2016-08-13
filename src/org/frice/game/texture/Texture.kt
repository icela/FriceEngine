package org.frice.game.texture

import java.awt.Graphics2D
import java.awt.Image

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
interface Texture {
	val image: Image
	fun getGraphics(): Graphics2D
}