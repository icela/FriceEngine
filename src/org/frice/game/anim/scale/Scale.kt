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

	private var cache: Double = start
	override val after: DoublePair
		get() {
			val pair = DoublePair.from1000((now - cache) * x, (now - cache) * y)
			cache = now
			return pair
		}
}