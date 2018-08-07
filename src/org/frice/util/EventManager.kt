package org.frice.util

import org.frice.event.DelayedEvent
import org.frice.util.time.FClock
import java.util.*

/**
 * Used to manage delayed events.
 *
 * @author ice1000
 * @since v1.7.4
 */
class EventManager {
	private val queue = PriorityQueue<DelayedEvent>()
	fun insert(event: DelayedEvent) = queue.offer(event)
	fun clear() = queue.clear()

	/**
	 * Should be invoked in onRefresh.
	 * It's recommended to run this as frequently as possible.
	 */
	tailrec fun check() {
		if (queue.isNotEmpty() && queue.peek().millisFromStart <= FClock.current) {
			queue.remove().let { if (!it.isCancelled) it.`{-# event #-}`.run() }
			check()
		}
	}
}
