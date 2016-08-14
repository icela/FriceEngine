package org.frice.game.spirit

import org.frice.game.resource.FResource

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
interface FObject {
	var id: Int
	val x: Int
	val y: Int
	fun getResource(): FResource
}