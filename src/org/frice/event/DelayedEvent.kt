package org.frice.event

import org.frice.obj.SideEffect
import org.frice.utils.time.FClock

/**
 * @author ice1000
 * @since v1.7.4
 * @see org.frice.utils.EventManager
 * @param millisFromStart milli seconds from the very beginning
 * @param `{-# event #-}` the event that is going to happen
 */
class DelayedEvent(
	val millisFromStart: Long,
	@JvmField internal val `{-# event #-}`: SideEffect) : Comparable<DelayedEvent> {
	var isReverted = false

	companion object Factory {
		/**
		 * @author ice1000
		 * @param millisFromNow milli seconds from now
		 * @param event the event that is going to happen
		 */
		@JvmStatic
		fun millisFromNow(millisFromNow: Long, event: SideEffect)
			= DelayedEvent(millisFromNow + FClock.current, event)
	}

	override operator fun compareTo(other: DelayedEvent)
		= millisFromStart.compareTo(other.millisFromStart)
}