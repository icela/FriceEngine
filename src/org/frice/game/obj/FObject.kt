package org.frice.game.obj

import org.frice.game.anim.FAnim
import org.frice.game.resource.FResource
import java.util.*

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
interface FObject {
	var id: Int
	var x: Int
	var y: Int
	var anims: ArrayList<FAnim>
	fun getResource(): FResource
}