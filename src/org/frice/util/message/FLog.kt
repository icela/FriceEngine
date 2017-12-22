package org.frice.util.message

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
object FLog {
	@JvmStatic
	fun v(message: Any?) = verbose(message)

	@JvmStatic
	fun d(message: Any?) = debug(message)

	@JvmStatic
	fun i(message: Any?) = info(message)

	@JvmStatic
	fun w(message: Any?) = warning(message)

	@JvmStatic
	fun e(message: Any?) = error(message)

	@JvmStatic
	fun verbose(message: Any?) {
		if (level >= VERBOSE) println(message)
	}

	@JvmStatic
	fun debug(message: Any?) {
		if (level >= DEBUG) println(message)
	}

	@JvmStatic
	fun info(message: Any?) {
		if (level >= INFO) println(message)
	}

	@JvmStatic
	fun warning(message: Any?) {
		if (level >= WARN) println(message)
	}

	@JvmStatic
	fun error(message: Any?) {
		if (level >= ERROR) System.err.println(message)
	}

	@JvmStatic
	var level = 50

	const val VERBOSE = 40
	const val DEBUG = 30
	const val INFO = 20
	const val WARN = 10
	const val ERROR = 0
}
