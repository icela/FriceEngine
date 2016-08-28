package org.frice.game.obj

import org.frice.game.obj.collide.CollideBox

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.4
 */
abstract class PhysicalObject : AbstractObject(), CollideBox, FContainer {
	open var died = false
	var mass = 1.0
		set(value) {
			if (value <= 0) field = 0.00001
			else field = value
		}
}