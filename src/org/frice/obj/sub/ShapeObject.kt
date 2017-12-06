package org.frice.obj.sub

import org.frice.anim.move.DoublePair
import org.frice.obj.FObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.shape.FShapeInt
import org.frice.utils.shape.FShapeQuad

/**
 * an object with a utils and a shape, used to create an simple object quickly
 * instead of load from an image file.
 *
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.1.1
 */
open class ShapeObject
@JvmOverloads
constructor(
	var res: ColorResource,
	val shape: FShapeInt,
	override var x: Double = 0.0,
	override var y: Double = 0.0,
	id: Int = -1) : FShapeQuad, FObject() {

	init {
		this.id = id
	}

	var collisionBox: FShapeQuad? = null
	override val box: FShapeQuad get() = collisionBox ?: this

	private var scale = DoublePair(1.0, 1.0)

	override var height: Double
		get() = (shape.height * scale.y)
		set(value) {
			shape.height = (value / scale.y).toInt()
		}

	override var width: Double
		get () = (shape.width * scale.x)
		set(value) {
			shape.width = (value / scale.x).toInt()
		}

	override var died = false

	override val resource get () = res

	override fun scale(x: Double, y: Double) {
		scale.x += x
		scale.y += y
	}

	override fun equals(other: Any?): Boolean {
		if (other == null || other !is FObject) return false
		if ((id != -1 && id == other.id) || this === other) return true
		return false
	}

	/** auto-generated. */
	override fun hashCode(): Int {
		if (id != -1) return id
		var result = res.hashCode()
		result = 31 * result + shape.hashCode()
		result = 31 * result + x.hashCode()
		result = 31 * result + y.hashCode()
		result = 31 * result + (collisionBox?.hashCode() ?: 0)
		result = 31 * result + scale.hashCode()
		result = 31 * result + died.hashCode()
		return result
	}
}