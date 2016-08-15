package org.frice.game.resource.image

import org.frice.game.resource.FResource
import java.awt.Image

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class ImageResource : FResource {

	abstract val image: Image

	override fun getResource() = image

}