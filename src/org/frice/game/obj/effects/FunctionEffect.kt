package org.frice.game.obj.effects

import org.frice.game.obj.sub.ImageObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.graphics.FunctionResource

/**
 * Created by ice1000 on 2016/8/24.
 *
 * @author ice1000
 * @since v0.4.1
 */
class FunctionEffect(f: FunctionResource, override var x: Double, override var y: Double) :
		ImageObject(f.getResource(), x, y) {

	constructor(function: Function, x: Double, y: Double, width: Int, height: Int) :
	this(FunctionResource(ColorResource.BLUE, { x -> function.call(x) }, width, height), x, y)



}
