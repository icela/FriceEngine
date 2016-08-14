package org.frice.utils.error.log

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
object FLog {
	fun v(e: Any?) = println(e?.toString())
	fun d(e: Any?) = println(e?.toString())
	fun i(e: Any?) = println(e?.toString())
	fun w(e: Any?) = println(e?.toString())
	fun e(e: Any?) = System.err.println(e?.toString())
}