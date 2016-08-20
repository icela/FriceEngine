package org.frice.game.utils.time

/**
 * @param times if the value is -1, it will loop.
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class FTimer(protected val time: Int, times: Int) {
	constructor(time: Int) : this(time, -1)

	var times = times
		private set
	private var start = System.currentTimeMillis()

	fun ended(): Boolean = if (System.currentTimeMillis() - start > time && times != 0) {
		start = System.currentTimeMillis()
		if (times > 0) times--
		true
	} else false
}