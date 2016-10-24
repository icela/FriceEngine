package org.frice.game.utils.message.log

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
object FLog {
	@JvmStatic
	fun v(e: Any?) = verbose(e)

	@JvmStatic
	fun d(e: Any?) = debug(e)

	@JvmStatic
	fun i(e: Any?) = info(e)

	@JvmStatic
	fun w(e: Any?) = warning(e)

	@JvmStatic
	fun e(e: Any?) = error(e)

	@JvmStatic
	fun verbose(e: Any?) = println(e?.toString())

	@JvmStatic
	fun debug(e: Any?) = println(e?.toString())

	@JvmStatic
	fun info(e: Any?) = println(e?.toString())

	@JvmStatic
	fun warning(e: Any?) = println(e?.toString())

	@JvmStatic
	fun error(e: Any?) = System.err.println(e?.toString())
}