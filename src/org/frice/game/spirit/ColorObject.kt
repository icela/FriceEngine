package org.frice.game.spirit

import org.frice.game.resource.ColorResource

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class ColorObject(val res: ColorResource, override var id: Int, override val x: Int, override val y: Int) : FObject {

	constructor(res: ColorResource, x: Int, y: Int) : this(res, -1, x, y)

	constructor(res: ColorResource, id: Int) : this(res, id, 0, 0)

	constructor(res: ColorResource) : this(res, -1)

	override fun getResource() = res

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || other !is ColorObject) return false
		if (res == other.res && id == other.id) return true
		return true
	}

	override fun hashCode(): Int {
		var result = res.hashCode()
		result = 31 * result + id
		return result
	}

}