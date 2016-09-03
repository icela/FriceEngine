package org.frice.game.special.gal

import org.frice.game.Game
import org.frice.game.event.OnClickEvent
import org.frice.game.obj.button.FButton
import org.frice.game.obj.button.ImageButton
import org.frice.game.obj.button.SimpleButton
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.audio.AudioManager
import org.frice.game.utils.kotlin.loop
import java.io.File
import java.util.*

/**
 * Created by ice1000 on 2016/9/3 0003.
 *
 * @author ice1000
 * @since v0.5
 */
class GalGame() : Game() {

	companion object {
		@JvmStatic val POSITION_LEFT = 0
		@JvmStatic val POSITION_MIDDLE = 1
		@JvmStatic val POSITION_RIGHT = 2
	}

	private val stepSequence = ArrayList<Step>()
	private val stepMarked = ArrayList<Int>()

	protected var step = 0
		private set

	private fun nextStep(skip: Boolean = false) {
		++step
		var recursive = false
		val now = stepSequence.first()
		when (now) {
			is GalBackground -> {
				back = now.image
			}
			is GalText -> {
			}
			is Gal立ち絵 -> {
				when (now.position) {
					POSITION_LEFT -> {
					}
					POSITION_MIDDLE -> {
					}
					POSITION_RIGHT -> {
					}
				}
			}
			is GalOptions -> {
				val list = listOf<SimpleButton>()
			}
			is GalAudio -> {
				if (!skip) AudioManager.play(now.file)
				stepSequence.removeAt(0)
			}
			is GalSkip -> {
				stepSequence.removeAt(0)
				recursive = true
				loop(if (now.isAbsolute) now.index - step else now.index) { nextStep(true) }
			}
		}
		if (!recursive) stepSequence.removeAt(0)
	}

	/**
	 * init the data and start playing game
	 */
	protected fun gameStart() {
		step = 0
		nextStep()
	}

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

	/**
	 * an option
	 */
	interface GalOption {
		val button: FButton
	}

	inner class GalTextOption(val text: String, val target: Int) : GalOption {
		override val button = SimpleButton(text, width / 4.0, 0.0, width / 2.0, 25.0)
	}

	class GalImageOption(val imageButton: ImageButton) : GalOption {
		override val button = imageButton
	}

	/**
	 * text
	 */
	protected inner class GalText(val text: String) : Step()

	/**
	 * background
	 */
	protected inner class GalBackground(val image: ImageResource) : Step()

	/**
	 * 立绘, 立ち絵
	 * @see GalTaChiE
	 */
	protected open inner class Gal立ち絵(val image: ImageResource, val position: Int) : Step()

	/**
	 * alias of 立ち絵
	 * @see Gal立ち絵
	 */
	protected inner class GalTaChiE(imageResource: ImageResource, position: Int) : Gal立ち絵(imageResource, position)

	protected inner class GalOptions(val list: List<GalOption>) : Step()
	protected inner class GalSkip(val index: Int) : Step() {
		var isAbsolute = false
			private set

		constructor(index: Int, isAbsolute: Boolean) : this(index) {
			this.isAbsolute = isAbsolute
		}
	}

	protected inner class GalAudio(val file: File) : Step() {
		constructor(path: String) : this(File(path))
	}
}