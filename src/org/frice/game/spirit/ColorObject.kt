package org.frice.game.spirit

import org.frice.game.resource.ColorResource

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class ColorObject(val res: ColorResource, override var id: Int) : FObject {
	override fun getResource() = res
}