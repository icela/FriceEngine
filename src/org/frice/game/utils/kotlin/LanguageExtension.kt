package org.frice.game.utils.kotlin

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

inline fun <T> T.loop(count: Int, block: T.(Int) -> Unit): T {
	for (index in 0..count - 1) block.invoke(this, index)
	return this
}

inline fun <T> T.loopIf(condition: () -> Boolean, block: T.() -> Unit): T {
	while (true) if (condition.invoke()) block.invoke(this)
}

inline fun <T> T.forceRun(block: T.() -> Unit): T {
	try {
		block.invoke(this)
	} catch (e: Throwable) {
	}
	return this
}

inline fun <T> T.forceGet(default: Any, block: T.() -> Any): Any {
	return try {
		block.invoke(this)
	} catch (e: Throwable) {
		default
	}
}

inline fun <T> T.forceLoop(block: T.() -> Unit) = forceRun { loop(block) }

fun <T> T.pause(length: Int) = pause(length.toLong())

fun <T> T.pause(length: Double) = pause(length.toLong())

fun <T> T.pause(length: Long): T {
	Thread.sleep(length)
	return this
}

/**
 * an anko-like async block
 */
inline fun <T> T.async(crossinline block: T.() -> Unit): T {
	Thread({ block.invoke(this) }).start()
	return this
}