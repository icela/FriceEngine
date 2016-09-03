package org.frice.game.special.gal

import org.frice.game.Game
import org.frice.game.event.OnClickEvent
import org.frice.game.resource.image.ImageResource
import java.io.File
import java.util.*

/**
 * Created by ice1000 on 2016/9/3 0003.
 *
 * @author ice1000
 */
class GalGame() : Game() {

	companion object {
		@JvmStatic val POSITION_LEFT = 0
		@JvmStatic val POSITION_MIDDLE = 1
		@JvmStatic val POSITION_RIGHT = 2
	}

	private val stepSequence = ArrayList<Step>()
	private val stepMarked = ArrayList<Int>()

	private var step = 0

	private fun nextStep() {
		++step
		when (stepSequence.first()) {
			is GalBackground -> {}
			is GalText -> {}
			is Gal立ち絵 -> {}
			is GalOptions -> {}
		}
		stepSequence.removeAt(0)
	}
	
	protected fun gameStart() = nextStep()

	protected fun load(path: String) = ImageResource.fromPath(path)
	protected fun load(file: File) = ImageResource.fromFile(file)

	protected fun addStep(o: Step) {
		stepSequence.add(o)
		if (o.marked) stepMarked.add(stepSequence.lastIndex)
	}

	override fun onClick(e: OnClickEvent) {
		super.onClick(e)
		nextStep()
	}

	protected inner abstract class Step() {
		var marked = false
			private set

		fun mark() = {
			marked = true
		}
	}

	class GalOption(val text: String)

	/**
	 * text
	 */
	protected inner class GalText(val text: String) : Step()

	/**
	 * background
	 */
	protected inner class GalBackground(val imageResource: ImageResource) : Step()

	/**
	 * 立绘
	 * 立ち絵
	 */
	protected open inner class Gal立ち絵(val imageResource: ImageResource, val position: Int) : Step()

	protected inner class GalTaChiE(imageResource: ImageResource, position: Int) : Gal立ち絵(imageResource, position)

	protected inner class GalOptions(val list: List<GalOption>) : Step()
}