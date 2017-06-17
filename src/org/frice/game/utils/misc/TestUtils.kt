package org.frice.game.utils.misc

/**
 * Created by ice1000 on 2016/9/9 0009.
 *
 * @author ice1000
 * @since v0.5.1
 */

inline fun assert1(block: () -> Boolean) {
	if (!block()) throw AssertionError()
}

infix fun <Any> Any?.shouleBe(o: Any?) = this === o || null != this && equals(o)
