package org.frice.anim.scale

import org.frice.anim.FAnim
import org.frice.anim.move.DoublePair
import org.frice.anim.move.DoublePair.Factory.fromK

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
			val pair = fromK((now - cache) * x, (now - cache) * y)
			cache = now
			return pair
		}
}