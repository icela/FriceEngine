package org.frice.obj.sub

import org.frice.resource.image.ImageResource

/**
 * Not finished yet.
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 */
class BoneObject(res: ImageResource, x: Double, y: Double, id: Int) : ImageObject(res, x, y, id) {
	override var died = false
}