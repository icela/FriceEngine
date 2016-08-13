package org.frice.game.texture

import java.awt.Graphics2D
import java.io.File
import javax.imageio.ImageIO

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class FileTexture(file: File) : Texture {

	override val image = ImageIO.read(file)!!

	constructor(path: String) : this(File(path))

	override fun getGraphics() = image.graphics as Graphics2D
}