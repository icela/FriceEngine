package org.frice.game.obj.collide

import org.frice.game.obj.FObject

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3
 */
interface CollideBox {
	fun isCollide(other: FObject): Boolean
}