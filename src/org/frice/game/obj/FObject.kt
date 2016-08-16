package org.frice.game.obj

import org.frice.game.anim.FAnim
import org.frice.game.obj.collide.CollideBox
import org.frice.game.resource.FResource
import org.frice.game.utils.shape.FShape
import java.util.*

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
interface FObject : CollideBox {
	var id: Int
	var x: Double
	var y: Double
	val width: Double
	val height: Double
	val anims: ArrayList<FAnim>
	val targets: ArrayList<FObject>
	val shape: FShape
	fun getResource(): FResource
	fun move(p: Pair<Double, Double>)
	fun scale(p: Pair<Double, Double>)

	fun rectCollide(rect1: FObject, rect2: FObject) = rect1.x + rect1.width >= rect2.x &&
			rect2.y <= rect1.y + rect1.height &&
			rect1.x <= rect2.x + rect2.width &&
			rect1.y <= rect2.y + rect2.height
}