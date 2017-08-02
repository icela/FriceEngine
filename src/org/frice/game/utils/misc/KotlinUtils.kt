@file:JvmName("Utils")
package org.frice.game.utils.misc

/**
 * Kotlin language extension
 * for Kotlin only
 *
 *
 * Created by ice1000 on 2016/8/17.
 * @author ice1000
 * @since v0.3.2
 */

inline fun loop(block: () -> Unit) {
	while (true) block()
}

inline fun loop(count: Int, block: (Int) -> Unit) = repeat(count, block)

inline fun forceRun(block: () -> Unit) {
	try {
		block()
	} catch (e: Throwable) {
	}
}

inline fun <T> forceGet(default: T, block: () -> T): T = try {
	block()
} catch (e: Throwable) {
	default
}

fun pause(length: Int) = pause(length.toLong())

fun pause(length: Double) = pause(length.toLong())

fun pause(length: Long) = Thread.sleep(length)

inline fun unless(condition: Boolean, block: () -> Unit) {
	if (!condition) block()
}

inline fun until(condition: Boolean, block: () -> Unit) {
	while (!condition) block()
}

/**
 * an anko-like async block
 */
inline fun async(crossinline block: () -> Unit) = Thread { block() }.start()
