package org.frice.game.anim.scale

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
class SimpleScale(val x: Double, val y: Double) : ScaleAnim {
	override fun getAfter() = Pair(x, y)
}