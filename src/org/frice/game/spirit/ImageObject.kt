package org.frice.game.spirit

import org.frice.game.resource.ImageResource

/**
 * Base GameObject class
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class ImageObject(val res: ImageResource, override var id: Int, override var x: Int, override var y: Int) : FObject {
	
	override fun getResource() = res

	constructor(res: ImageResource, id: Int) : this(res, id, 0, 0)

	constructor(res: ImageResource) : this(res, -1, 0, 0)

	constructor(res: ImageResource, x: Int, y: Int) : this(res, -1, x, y)

	/**
	 * @return returns an image instance of this res
	 */
	fun getImage() = res.image

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is ImageObject) return false
		if ((id != -1 && id == other.id) ||
				res == other.res && x == other.x && y == other.y) return true
		return false
	}

	override fun hashCode(): Int {
		var result = res.hashCode()
		result = 31 * result + id
		result = 31 * result + x
		result = 31 * result + y
		return result
	}
}