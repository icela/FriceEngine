package org.frice.utils

import org.frice.event.DelayedEvent
import org.frice.utils.time.FClock
import java.util.*

/**
 * @author ice1000
 * @since v1.7.4
 */
class EventManager {
	private val queue = PriorityQueue<DelayedEvent>()
	fun insert(event: DelayedEvent) = queue.offer(event)

	tailrec fun check() {
		val latest = queue.peek() ?: return
		if (latest.millisFromStart <= FClock.current) {
			latest.event()
			check()
		}
	}
}
