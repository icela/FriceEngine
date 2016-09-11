package org.frice.game.anim.scale

import org.frice.game.anim.FAnim
import org.frice.game.anim.move.DoublePair

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */
abstract class ScaleAnim : FAnim() {
	abstract val after: DoublePair
}

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
class SimpleScale(var x: Double, var y: Double) : ScaleAnim() {
	private val timeFromStart: Double
		get() = System.currentTimeMillis().toDouble() - start + 1

	override val after: DoublePair
		get() = DoublePair(x * timeFromStart, y * timeFromStart)

}