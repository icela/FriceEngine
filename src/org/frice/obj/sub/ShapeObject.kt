package org.frice.obj.sub

import org.frice.obj.FObject
import org.frice.platform.owners.ColorOwner
import org.frice.resource.graphics.ColorResource
import org.frice.utils.shape.FShapeInt

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
	override var color: ColorResource,
	val shape: FShapeInt,
	x: Double = 0.0,
	y: Double = 0.0,
	id: Int = -1) : FObject(x, y), ColorOwner {

	init {
		this.id = id
	}

	private var scaleX: Double = 1.0
	private var scaleY: Double = 1.0

	override var height: Double
		get() = (shape.height * scaleY)
		set(value) {
			shape.height = (value / scaleY).toInt()
		}

	override var width: Double
		get () = (shape.width * scaleX)
		set(value) {
			shape.width = (value / scaleX).toInt()
		}

	override var died = false

	override val resource get () = color

	override fun scale(x: Double, y: Double) {
		scaleX += x
		scaleY += y
	}

	override fun equals(other: Any?): Boolean {
		if (other == null || other !is FObject) return false
		if ((id != -1 && id == other.id) || this === other) return true
		return false
	}

	/** auto-generated. */
	override fun hashCode(): Int {
		var result = color.hashCode()
		result = 31 * result + shape.hashCode()
		result = 31 * result + x.hashCode()
		result = 31 * result + y.hashCode()
		result = 31 * result + scaleX.hashCode()
		result = 31 * result + scaleY.hashCode()
		result = 31 * result + died.hashCode()
		return result
	}
}