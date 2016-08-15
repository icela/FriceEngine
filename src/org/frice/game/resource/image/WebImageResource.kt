package org.frice.game.resource.image

import java.net.URL
import javax.imageio.ImageIO

/**
 * Image Resource from internet
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */
class WebImageResource(val url: URL) : ImageResource() {

	constructor(url: String) : this(URL(url))

	override val image = ImageIO.read(url)!!

}