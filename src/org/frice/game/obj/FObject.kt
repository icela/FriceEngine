package org.frice.game.obj

import org.frice.game.anim.FAnim
import org.frice.game.resource.FResource
import java.util.*

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
interface FObject {
	var id: Int
	var x: Double
	var y: Double
	val anims: ArrayList<FAnim>
	fun getResource(): FResource
	fun move(p: Pair<Double, Double>)
	fun scale(p: Pair<Double, Double>)
}