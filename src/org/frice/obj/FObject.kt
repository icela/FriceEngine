package org.frice.obj

import org.frice.anim.FAnim
import org.frice.platform.FriceImage
import org.frice.resource.FResource
import org.frice.utils.shape.FShapeQuad
import java.util.*

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
abstract class FObject : PhysicalObject() {
	var id = -1
	val anims = LinkedList<FAnim>()
	override var rotate = 0.0

	abstract val resource: FResource

	abstract fun scale(x: Double, y: Double)

	open infix fun rotate(angle: Double) {
		rotate += angle
	}

	private fun squaredDelta(d1: Double, d2: Double) = (d1 - d2) * Math.abs(d1 - d2)

	@Suppress("FunctionName")
	internal fun `{-# runAnims #-}`() = anims.forEach { a -> a.`{-# do #-}`(this) }

	fun addAnim(anim: FAnim) = anims.add(anim)

	fun stopAnims() = anims.clear()

	interface ImageOwner : Collidable, FShapeQuad {
		val image: FriceImage

		override val width: Double get() = image.width.toDouble()
		override val height: Double get() = image.height.toDouble()

		var collisionBox: FShapeQuad?
		override val box: FShapeQuad get() = collisionBox ?: this
	}
}