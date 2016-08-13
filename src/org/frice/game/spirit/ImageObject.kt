package org.frice.game.spirit

import org.frice.game.resource.ImageResource

/**
 * Base GameObject class
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class ImageObject(override var resource: ImageResource, override var id: Int, var x: Int, var y: Int) : BaseObject {

	constructor(resource: ImageResource, id: Int) : this(resource, id, 0, 0)

	constructor(resource: ImageResource) : this(resource, -1, 0, 0)

	/**
	 * @return returns an image instance of this resource
	 */
	fun getImage() = resource.image

	override fun equals(other: Any?): Boolean {
		if (other == null || other !is ImageObject) return false
		if ((id == -1 || id == other.id) && resource == other.resource && x == other.x && y == other.y) return true
		return false
	}

	override fun hashCode(): Int {
		var result = resource.hashCode()
		result = 31 * result + id
		result = 31 * result + x
		result = 31 * result + y
		return result
	}
}