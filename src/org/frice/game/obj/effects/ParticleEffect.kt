package org.frice.game.obj.effects

import org.frice.game.anim.FAnim
import org.frice.game.event.OnCollideEvent
import org.frice.game.obj.FObject
import org.frice.game.resource.graphics.ParticleResource
import org.frice.game.utils.graphics.shape.FRectangle
import java.util.*

/**
 * Created by ice1000 on 2016/8/17.
 * @author ice1000
 * @since 0.3.2
 */
class ParticleEffect(var res: ParticleResource, override var x: Double, override var y: Double) : FObject {
	override var id = -1

	override val anims = ArrayList<FAnim>()
	override val targets = ArrayList<Pair<FObject, OnCollideEvent>>()

	override val collideBox = FRectangle(0, 0)

	override val width: Double
		get() = res.x.toDouble()
	override val height: Double
		get() = res.y.toDouble()

	override fun getResource() = res

	override fun move(p: Pair<Double, Double>) {
		x += p.first
		y += p.second
	}

	override fun scale(p: Pair<Double, Double>) {
		res.x = (res.x * p.first).toInt()
		res.y = (res.y * p.second).toInt()
	}

	override fun isCollide(other: FObject) = false
}
