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

inline fun <T> T.loop(count: Int, block: T.(Int) -> Unit): T {
	for (index in 0..count - 1) block(this, index)
	return this
}

inline fun loop(count: Int, block: (Int) -> Unit) = repeat(count, block)

inline fun loopIf(condition: () -> Boolean, block: () -> Unit) {
	while (true) if (condition()) block()
}

inline fun <T> T.forceRun(block: T.() -> Unit): T {
	try {
		block(this)
	} catch (e: Throwable) {
	}
	return this
}

inline fun forceRun(block: () -> Unit) {
	try {
		block()
	} catch (e: Throwable) {
	}
}

inline fun forceGet(default: Any, block: () -> Any) = try {
	block()
} catch (e: Throwable) {
	default
}

/**
 * it will exit when meets an uncaught exception.
 */
inline fun forceLoop(block: () -> Unit) = forceRun { loop(block) }

fun pause(length: Int) = pause(length.toLong())

fun pause(length: Double) = pause(length.toLong())

fun pause(length: Long) = Thread.sleep(length)

/**
 * an anko-like async block
 */
inline fun async(crossinline block: () -> Unit) = Thread({ block() }).start()

/**
 * times once, get a pair
 */
operator fun Pair<Double, Double>.times(double: Double): Pair<Double, Double> = Pair(first / double, second / double)