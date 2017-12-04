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
	override val collideBox: FShapeInt,
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
		get() = (collideBox.height * scale.y)
		set(value) {
			collideBox.height = (value / scale.y).toInt()
		}

	override var width: Double
		get () = (collideBox.width * scale.x)
		set(value) {
			collideBox.width = (value / scale.x).toInt()
		}

	override var died = false

	override fun getResource() = res

	override fun scale(x: Double, y: Double) {
		scale.x += x
		scale.y += y
	}

	override fun equals(other: Any?): Boolean {
		if (other == null || other !is FObject) return false
		if ((id != -1 && id == other.id) || this === other) return true
		return false
	}
}