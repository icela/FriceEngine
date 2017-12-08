package org.frice.utils.shape

/**
 * @author ice1000
 * @since v1.6.7
 * @see org.frice.utils.shape.FQuad
 */
interface FShapeQuad {
	val x: Double
	val y: Double
	val width: Double
	val height: Double

	fun boxByOffset(topBottom: Double, leftRight: Double) = boxByOffset(topBottom, topBottom, leftRight, leftRight)
	fun boxByOffset(allDirection: Double) = if (allDirection == 0.0) this else boxByOffset(allDirection, allDirection)
	fun boxByOffset(top: Double, bottom: Double, left: Double, right: Double): FShapeQuad =
		object : FShapeQuad {
			override val x get() = this@FShapeQuad.x + left
			override val y get() = this@FShapeQuad.y + top
			override val width get () = this@FShapeQuad.width - left - right
			override val height get() = this@FShapeQuad.height - top - bottom
		}
}

data class FQuad(
	override var x: Double,
	override var y: Double,
	override var width: Double,
	override var height: Double) : FShapeQuad
