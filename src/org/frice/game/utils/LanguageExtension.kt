package org.frice.game.utils

/**
 * Kotlin language extension
 *
 *
 * Created by ice1000 on 2016/8/17.
 * @author ice1000
 * @since v0.3.2
 */

inline fun <T> T.loop(block: T.() -> Unit): T {
	while (true) block.invoke(this)
}

inline fun <T> T.loopIf(condition: () -> Boolean, block: T.() -> Unit): T {
	while (true) if (condition.invoke()) block.invoke(this)
}

inline fun <T> T.forceRun(block: T.() -> Unit): T {
	try {
		block.invoke(this)
	} catch (e: Error) {
	} catch (e: Exception) {
	} finally {
	}
	return this
}

inline fun <T> T.forceLoop(block: T.() -> Unit) = forceRun { loop(block) }

fun <T> T.pause(length: Int) = pause(length.toLong())

fun <T> T.pause(length: Double) = pause(length.toLong())

fun <T> T.pause(length: Long): T {
	Thread.sleep(length)
	return this
}