package org.frice.event

import org.frice.obj.SideEffect
import org.frice.utils.time.FClock

/**
 * @author ice1000
 * @since v1.7.4
 * @see org.frice.utils.EventManager
 */
class DelayedEvent(val millisFromStart: Long, val event: SideEffect) : Comparable<DelayedEvent> {
	companion object Factory {
		@JvmStatic
		fun millisFromNow(millisFromNow: Long, event: SideEffect)
			= DelayedEvent(millisFromNow + FClock.current, event)
	}

	override operator fun compareTo(other: DelayedEvent)
		= millisFromStart.compareTo(other.millisFromStart)
}