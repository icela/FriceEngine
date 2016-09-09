package org.frice.game.utils.misc

/**
 * Created by ice1000 on 2016/9/9 0009.
 *
 * @author ice1000
 * @since v0.5.1
 */

class AssertionException() : Exception()

inline fun assert(block: () -> Boolean) {
	if (!block()) throw AssertionException()
}

fun assert(boolean: Boolean) {
	if (boolean) throw AssertionException()
}