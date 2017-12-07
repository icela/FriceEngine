package org.frice.utils

import org.frice.event.DelayedEvent
import java.util.*

/**
 * @author ice1000
 * @since v1.7.4
 */
class EventManager {
	private val queue = PriorityQueue<DelayedEvent>()
}