package org.frice.utils.time

import org.frice.utils.misc.unless

/**
 * Created by ice1000 on 2016/10/16.
 *
 * @author ice1000
 */
object FClock {
	var started = true
		internal set
	var startTicks = System.currentTimeMillis()
		internal set
	var pauseTicks = startTicks
		internal set

	val current: Long
		get() = if (started) System.currentTimeMillis() - startTicks else pauseTicks - startTicks

	fun init() {
		startTicks = System.currentTimeMillis()
		started = true
	}

	fun resume() {
		if (started) return
		startTicks += System.currentTimeMillis() - pauseTicks
		started = true
	}

	fun pause() {
		unless(started) {
			return
		}
		pauseTicks = System.currentTimeMillis()
		started = false
	}
}