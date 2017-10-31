package org.frice.obj.sub

import org.frice.anim.move.DoublePair
import org.frice.obj.CollideBox
import org.frice.obj.FObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.graphics.shape.FRectangle
import org.frice.utils.graphics.shape.FShapeInt

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
		var res: org.frice.resource.graphics.ColorResource,
		override val collideBox: FShapeInt,
		override var x: Double = 0.0,
		override var y: Double = 0.0,
		override var id: Int = -1) : org.frice.obj.FObject() {
	private var scale = org.frice.anim.move.DoublePair(1.0, 1.0)

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

	override fun isCollide(other: org.frice.obj.CollideBox): Boolean = when (other) {
		is org.frice.obj.sub.ShapeObject -> when (other.collideBox) {
			is FRectangle -> when (collideBox) {
				is FRectangle -> this rectCollideRect other
//				is FOval ->
				else -> this rectCollideRect other
			}
//			is FOval ->
			else -> this rectCollideRect other
		}
		is org.frice.obj.sub.ImageObject -> when (collideBox) {
			is FRectangle -> this rectCollideRect other
			else -> this rectCollideRect other
		}
		else -> false
	}

	override fun getResource() = res

	override fun scale(x: Double, y: Double) {
		scale.x += x
		scale.y += y
	}

	override fun equals(other: Any?): Boolean {
		if (other == null || other !is org.frice.obj.FObject) return false
		if ((id != -1 && id == other.id) || this === other) return true
		return false
	}
}