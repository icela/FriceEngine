package org.frice.event

import org.frice.util.time.FClock

/**
 * @author ice1000
 * @since v1.7.4
 * @see org.frice.util.EventManager
 * @param millisFromStart milli seconds from the very beginning
 * @param `{-# event #-}` the event that is going to happen
 */
class DelayedEvent(
	val millisFromStart: Long,
	@JvmField internal val `{-# event #-}`: Runnable) : Comparable<DelayedEvent> {
	var isCancelled = false

	/**
	 * Cancelling this event
	 * @author ice1000
	 * @since v1.8.0
	 */
	fun cancel() {
		isCancelled = true
	}

	companion object Factory {
		/**
		 * @author ice1000
		 * @param millisFromNow milli seconds from now
		 * @param event the event that is going to happen
		 */
		@JvmStatic
		fun millisFromNow(millisFromNow: Long, event: Runnable)
			= DelayedEvent(millisFromNow + FClock.current, event)
	}

	override operator fun compareTo(other: DelayedEvent)
		= millisFromStart.compareTo(other.millisFromStart)
}