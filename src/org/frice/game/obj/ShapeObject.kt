package org.frice.game.obj

import org.frice.game.anim.move.MoveAnim
import org.frice.game.resource.ColorResource
import org.frice.game.utils.shape.FShape
import java.util.*

/**
 * an object with a color and a shape, used to create an simple object quickly
 * instead of load from an image file.
 *
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
open class ShapeObject(val res: ColorResource, val shape: FShape, override var id: Int,
                       override var x: Double, override var y: Double) : FObject {
	override val anims: ArrayList<MoveAnim> = ArrayList()

	constructor(res: ColorResource, shape: FShape, x: Double, y: Double) : this(res, shape, -1, x, y)

	constructor(res: ColorResource, shape: FShape, id: Int) : this(res, shape, id, 0.0, 0.0)

	constructor(res: ColorResource, shape: FShape) : this(res, shape, -1)

	override fun getResource() = res

	override fun move(p: Pair<Double, Double>) {
		x += p.first
		y += p.second
	}

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