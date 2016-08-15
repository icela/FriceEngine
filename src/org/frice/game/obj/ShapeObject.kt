package org.frice.game.obj

import org.frice.game.resource.ColorResource
import org.frice.game.utils.shape.FShape

/**
 * an object with a color and a shape, used to create an simple object quickly
 * instead of load from an image file.
 *
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class ShapeObject(val res: ColorResource, val shape: FShape,
                  override var id: Int, override val x: Int, override val y: Int) : FObject {

	constructor(res: ColorResource, shape: FShape, x: Int, y: Int) : this(res, shape, -1, x, y)

	constructor(res: ColorResource, shape: FShape, id: Int) : this(res, shape, id, 0, 0)

	constructor(res: ColorResource, shape: FShape) : this(res, shape, -1)

	override fun getResource() = res

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is ShapeObject) return false
		if ((id != -1 && id == other.id) ||
				res == other.res && x == other.x && y == other.y) return true
		return true
	}

	override fun hashCode(): Int {
		var result = res.hashCode()
		result = 31 * result + id
		return result
	}

}