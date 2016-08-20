package org.frice.game.obj

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 * @since v0.4
 */
abstract class PhysicalObject : AbstractObject(), FContainer {
	open var died = false
}