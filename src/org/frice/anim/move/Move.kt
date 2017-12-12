@file:Suppress("NOTHING_TO_INLINE")

package org.frice.anim.move

import org.frice.anim.FAnim
import org.frice.obj.AbstractObject

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 */
abstract class MoveAnim : FAnim() {
	abstract val delta: DoublePair
}

/**
 * @author ice1000
 * @since v1.7.2
 */
abstract class SelfCenteredMoveAnim(val self: AbstractObject) : MoveAnim()

/**
 * Define your own move object as you like.
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
abstract class CustomMove : MoveAnim() {
	abstract fun getXDelta(timeFromBegin: Double): Double
	abstract fun getYDelta(timeFromBegin: Double): Double

	override val delta: DoublePair
		get() {
			val deltaTime = now - lastRefresh
			val pair = DoublePair(getXDelta(deltaTime), getYDelta(deltaTime))
			lastRefresh = now
			return pair
		}
}
/**
 * Define your own move object as you like.
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v1.7.4
 */
abstract class CustomMove2 : MoveAnim() {
	abstract fun getDelta(timeFromBegin: Double): DoublePair

	override val delta: DoublePair
		get() {
			val pair = getDelta(now - lastRefresh)
			lastRefresh = now
			return pair
		}
}