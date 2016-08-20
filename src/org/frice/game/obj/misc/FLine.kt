package org.frice.game.obj.misc

import org.frice.game.obj.AbstractObject
import org.frice.game.resource.graphics.ColorResource

/**
 * Created by ice1000 on 2016/8/19.
 *
 * @author ice1000
 */
class FLine(private var colorResource: ColorResource, override var x: Double, override var y: Double,
            var x2: Double, var y2: Double) : AbstractObject {

	constructor(x: Double, y: Double, x2: Double, y2: Double) : this(ColorResource.BLACK, x, y, x2, y2)
}