package org.frice.game.special.gal

import org.frice.game.Game
import org.frice.game.event.OnClickEvent
import org.frice.game.obj.AbstractObject
import org.frice.game.resource.image.ImageResource
import java.io.File
import java.util.*

/**
 * Created by ice1000 on 2016/9/3 0003.
 *
 * @author ice1000
 */
class GalGame() : Game() {

	private val stepList = ArrayList<AbstractObject>()

	private fun nextStep() {
		//
	}

	protected fun load(path: String) = ImageResource.fromPath(path)
	protected fun load(file: File) = ImageResource.fromFile(file)
	protected fun addStep(o: AbstractObject) {
		stepList.add(o)
	}

	override fun onClick(e: OnClickEvent) {
		super.onClick(e)
		nextStep()
	}
}