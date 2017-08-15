package org.frice.game.utils.time

/**
 * @param times if the value is -1, will loop.
 *
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.2
 */
class FTimeListener
@JvmOverloads
constructor(time: Int, times: Int = -1, val timeUp: () -> Unit) : FTimer(time, times) {

	fun check() {
		if (ended() && times != 0) timeUp()
	}
}

/**
 * @param times if the value is -1, it will loop.
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class FTimer
@JvmOverloads
constructor(var time: Int, times: Int = -1) {
	var times = times
		protected set
	private var start = Clock.current

	fun ended(): Boolean = if (Clock.current - start > time && times != 0) {
		start = Clock.current
		if (times > 0) times--
		true
	} else false
}
