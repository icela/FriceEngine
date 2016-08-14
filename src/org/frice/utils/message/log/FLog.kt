package org.frice.utils.message.log

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
object FLog {
	@JvmStatic fun v(e: Any?) = println(e?.toString())
	@JvmStatic fun d(e: Any?) = println(e?.toString())
	@JvmStatic fun i(e: Any?) = println(e?.toString())
	@JvmStatic fun w(e: Any?) = println(e?.toString())
	@JvmStatic fun e(e: Any?) = System.err.println(e?.toString())
}