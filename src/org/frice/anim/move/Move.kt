@file:Suppress("NOTHING_TO_INLINE")

package org.frice.anim.move

import org.frice.anim.FAnim
import org.frice.obj.AbstractObject
import org.frice.obj.FObject

/**
 * @author ice1000
 * @since v1.7.2
 */
abstract class SelfCenteredMoveAnim(val self: AbstractObject) : FAnim()

/**
 * Define your own move object as you like.
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
abstract class CustomMove : FAnim() {
	abstract fun getXDelta(timeFromBegin: Double): Double
	abstract fun getYDelta(timeFromBegin: Double): Double

	override fun `{-# do #-}`(obj: FObject) {
		val deltaTime = now - lastRefresh
		lastRefresh = now
		obj.move(getXDelta(deltaTime), getYDelta(deltaTime))
	}
}
