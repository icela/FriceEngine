package org.frice.game.spirit

import org.frice.game.resource.ColorResource

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
class ColorObject(val res: ColorResource, override var id: Int) : FObject {
	override fun getResource() = res

	override fun equals(other: Any?): Boolean{
		if (this === other) return true
		if (other?.javaClass != javaClass) return false

		other as ColorObject

		if (res != other.res) return false
		if (id != other.id) return false

		return true
	}

	override fun hashCode(): Int{
		var result = res.hashCode()
		result = 31 * result + id
		return result
	}

}