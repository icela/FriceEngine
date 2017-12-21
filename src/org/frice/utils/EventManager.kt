package org.frice.utils

import org.frice.event.DelayedEvent
import org.frice.utils.time.FClock
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
	 * It's recommended to invoke this as frequently as possible.
	 */
	tailrec fun check() {
		if (queue.isNotEmpty() && queue.peek().millisFromStart <= FClock.current) {
			queue.remove().let { if (!it.isReverted) it.`{-# event #-}`() }
			check()
		}
	}
}
