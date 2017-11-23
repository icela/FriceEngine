package org.frice.utils.time

/**
 * @param times if the value is -1, it will loop.
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class FTimer
@JvmOverloads
constructor(var time: Int, var times: Int = -1) {
	private var start = FClock.current
	private val isEnded: Boolean
		get() = FClock.current - start > time && times != 0

	fun ended(): Boolean = isEnded.apply {
		if (this) {
			start = FClock.current
			if (times > 0) times--
		}
	}
}
