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
	override val after: DoublePair
		get() {
			val deltaTime = now - lastRefresh
			val pair = fromK(deltaTime * x, deltaTime * y)
			lastRefresh = now
			return pair
		}
}