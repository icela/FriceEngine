package org.frice.game.obj

import org.frice.game.anim.MoveAnim
import org.frice.game.resource.FResource
import java.util.*

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
interface FObject {
	var id: Int
	var x: Int
	var y: Int
	val anims: ArrayList<MoveAnim>
	fun getResource(): FResource
	fun move(p: Pair<Int, Int>)
}