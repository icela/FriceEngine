package org.frice.utils.time

import org.frice.utils.unless

/**
 * Created by ice1000 on 2016/10/16.
 *
 * @author ice1000
 */
object FClock {
	@get:JvmStatic
	var started = true
		@JvmName(" ") internal set

	@get:JvmStatic
	var startTicks = System.currentTimeMillis()
		@JvmName("  ") internal set

	@get:JvmStatic
	var pauseTicks = startTicks
		@JvmName("   ") internal set

	@get:JvmStatic
	val current: Long
		get() = if (started) System.currentTimeMillis() - startTicks else pauseTicks - startTicks

	@JvmStatic
	fun init() {
		startTicks = System.currentTimeMillis()
		started = true
	}

	@JvmStatic
	fun resume() {
		if (started) return
		startTicks += System.currentTimeMillis() - pauseTicks
		started = true
	}

	@JvmStatic
	fun pause() {
		unless(started) {
			return
		}
		pauseTicks = System.currentTimeMillis()
		started = false
	}
}