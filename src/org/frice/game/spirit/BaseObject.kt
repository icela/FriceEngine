package org.frice.game.spirit

import org.frice.game.texture.Texture

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class BaseObject(var texture: Texture, val x: Int = 0, val y: Int = 0) {
	fun getImage() = texture.image
}