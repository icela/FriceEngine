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
interface FObject: CollideBox {
	var id: Int
	var x: Double
	var y: Double
	val width: Double
	val height: Double
	val anims: ArrayList<FAnim>
	val shape: FShape
	fun getResource(): FResource
	fun move(p: Pair<Double, Double>)
	fun scale(p: Pair<Double, Double>)

	fun rectCollide(o1: FObject, o2: FObject) {

	}
}