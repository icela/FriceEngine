package org.frice.game.spirit

import org.frice.game.texture.Texture

/**
 * Base GameObject class
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class ImageObject(var texture: Texture, var x: Int = 0, var y: Int = 0) {

	/**
	 * @return returns an image instance of this texture
	 */
	fun getImage() = texture.image

	override fun equals(other: Any?): Boolean {
		if (other == null || other !is ImageObject) return false
		if (texture == other.texture && x == other.x && y == other.y) return true
		return false
	}

	override fun hashCode(): Int {
		var result = texture.hashCode()
		result = 31 * result + x
		result = 31 * result + y
		return result
	}
}