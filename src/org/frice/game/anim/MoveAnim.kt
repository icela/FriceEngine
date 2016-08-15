package org.frice.game.anim

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 */
interface MoveAnim : FAnim {
	fun getDelta(): Pair<Double, Double>
}