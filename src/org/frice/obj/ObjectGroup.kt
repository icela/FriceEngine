package org.frice.obj

import org.frice.resource.FResource
import org.frice.resource.image.ImageResource.Factories.empty
import org.frice.utils.shape.FRectangle
import org.frice.utils.shape.FShapeQuad

class ObjectGroup
@JvmOverloads
constructor(val objs: MutableList<FObject> = emptyList<FObject>().toMutableList()) : FShapeQuad, FObject() {
	override var x = Double.MAX_VALUE
	override var y = Double.MAX_VALUE
	override var width = 0.0
	override var height = 0.0
	override val collideBox: FRectangle

	var collisionBox: FShapeQuad? = null
	override val box: FShapeQuad get() = collisionBox ?: this

	init {
		var r = 0.0
		var d = 0.0
		objs.forEach { o ->
			x = Math.min(x, o.x)
			y = Math.min(y, o.y)
			r = Math.max(r, o.x + o.width)
			d = Math.max(d, o.y + o.height)
		}
		width = r - x
		height = d - y
		collideBox = FRectangle(width, height)
	}

	override fun collides(other: Collidable) = objs.any { it.collides(other) }
	override val resource = empty()
	override fun scale(x: Double, y: Double) = objs.forEach { it.scale(x, y) }
	override fun move(p: org.frice.anim.move.DoublePair) = objs.forEach { it.move(p) }
	override fun rotate(angle: Double) = objs.forEach { it.rotate(angle) }

	fun addObject(vararg objects: FObject) = objects.forEach { objs.remove(it) }
	fun removeObject(vararg objects: FObject) = objects.forEach { objs.remove(it) }
}