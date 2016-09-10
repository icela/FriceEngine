package org.frice.game.obj.sub

import org.frice.game.resource.image.ImageResource

/**
 * Not finished yet.
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 */
class BoneObject(res: ImageResource, id: Int, x: Double, y: Double) : ImageObject(res, id, x, y) {
	override var died = false
}