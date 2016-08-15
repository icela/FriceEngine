package org.frice.game.utils.time

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class FTimer(protected val time: Int) {
	private var start = System.currentTimeMillis()

	fun ended(): Boolean = if (System.currentTimeMillis() - start > time) {
		start = System.currentTimeMillis()
		true
	} else false
}