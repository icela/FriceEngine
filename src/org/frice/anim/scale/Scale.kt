package org.frice.anim.scale

import org.frice.anim.FAnim
import org.frice.anim.move.DoublePair

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */
abstract class ScaleAnim : org.frice.anim.FAnim() {
	abstract val after: org.frice.anim.move.DoublePair
}

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
class SimpleScale(var x: Double, var y: Double) : org.frice.anim.scale.ScaleAnim() {

	private var cache: Double = start
	override val after: org.frice.anim.move.DoublePair
		get() {
			val pair = org.frice.anim.move.DoublePair.from1000((now - cache) * x, (now - cache) * y)
			cache = now
			return pair
		}
}