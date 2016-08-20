package org.frice.game.obj.effects

import org.frice.game.obj.PhysicalObject
import org.frice.game.obj.sub.ImageObject
import org.frice.game.resource.graphics.ParticleResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.shape.FRectangle

/**
 * Created by ice1000 on 2016/8/17.
 * @author ice1000
 * @since 0.3.2
 */
class ParticleEffect(private var resource: ParticleResource, override var x: Double, override var y: Double) :
		ImageObject(resource.getResource(), x, y) {
	override val collideBox = FRectangle(x.toInt(), y.toInt())

	override val width: Double
		get() = resource.x.toDouble()
	override val height: Double
		get() = resource.y.toDouble()
	override var died = false

	override fun getResource() = ImageResource.create(resource.getResource())

	override fun scale(p: Pair<Double, Double>) {
		resource.x = (resource.x * p.first).toInt()
		resource.y = (resource.y * p.second).toInt()
	}

	override fun isCollide(other: PhysicalObject) = false
}
