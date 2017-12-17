package org.frice.utils.data

import org.frice.utils.cast

/**
 * Created by ice1000 on 2016/8/20.
 *
 * @author ice1000
 * @since v0.4.1
 */
interface Database {
	fun insert(key: String, value: Any?)
	fun <T> queryT(key: String, default: T): T = cast(query(key, default))
	fun query(key: String, default: Any?): Any?
}
