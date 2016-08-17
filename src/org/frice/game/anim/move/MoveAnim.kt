package org.frice.game.anim.move

import org.frice.game.anim.FAnim

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.1
 */
internal abstract class MoveAnim : FAnim() {
	abstract fun getDelta(): Pair<Double, Double>
}