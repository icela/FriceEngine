package org.frice.game.resource.image

import java.awt.Rectangle

/**
 * create an image from a part of another image
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
class PartImageResource(origin: ImageResource, x: Int, y: Int, width: Int, height: Int) : ImageResource() {
	constructor(origin: ImageResource, part: Rectangle) : this(origin, part.x, part.y, part.width, part.height)

	override var image = origin.image.getSubimage(x, y, width, height)
}