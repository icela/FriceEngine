@file:Suppress("UNCHECKED_CAST")

package org.frice.game.utils.data

/**
 * Created by ice1000 on 2016/8/20.
 *
 * @author ice1000
 * @since v0.4.1
 */
interface Database {
	fun insert(key: String, value: Any?)
	fun <T> query(key: String, default: T): T
}
