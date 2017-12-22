package org.frice.platform.owner

import org.frice.platform.FriceImage
import org.frice.util.shape.FShapeQuad

interface ImageOwner : FShapeQuad {
	val image: FriceImage

	override val width: Double get() = image.width.toDouble()
	override val height: Double get() = image.height.toDouble()
}