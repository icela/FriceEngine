@file:JvmName("Utils")
@file:JvmMultifileClass

package org.frice.utils

/**
 * Created by ice1000 on 2016/9/9 0009.
 *
 * @author ice1000
 * @since v0.5.1
 */

infix fun <Any> Any?.shouldBe(o: Any?) = this === o || null != this && equals(o)
