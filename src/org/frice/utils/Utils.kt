@file:JvmName("Utils")
@file:JvmMultifileClass

/**
 * General purpose utilities
 *
 * Created by ice1000 on 2016/8/17.
 * @author ice1000
 * @since v0.3.2
 */
package org.frice.utils

inline fun loop(block: () -> Unit) {
	while (true) block()
}

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <R> cast(any: Any?): R = any as R

inline fun forceRun(block: () -> Unit) {
	try {
		block()
	} catch (e: Throwable) {
	}
}

inline fun <reified T> forceGet(default: T, block: () -> T): T = try {
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

fun Any.any2Map() = javaClass
	.declaredFields
	.map { it.name to it.get(this) }
	.toMap()
