package org.frice.special.gal

import org.frice.Game
import org.frice.event.OnClickEvent
import org.frice.obj.button.FButton
import org.frice.obj.button.ImageButton
import org.frice.obj.button.SimpleButton
import org.frice.obj.sub.ImageObject
import org.frice.resource.image.ImageResource
import org.frice.utils.audio.play
import org.frice.utils.misc.unless
import java.io.File
import java.util.*

/**
 * Easy framework to help you make a gal
 * Created by ice1000 on 2016/9/3 0003.
 *
 * @author ice1000
 * @since v0.5
 */
internal open class GalGame : org.frice.Game() {

	companion object {
		@JvmField val POSITION_LEFT = 0
		@JvmField val POSITION_MIDDLE = 1
		@JvmField val POSITION_RIGHT = 2
	}

	private val stepSequence = ArrayList<org.frice.special.gal.GalStep>()
	private val stepMarked = ArrayList<Int>()

	protected var step = 0
		private set

	private lateinit var leftTaChiE: org.frice.obj.sub.ImageObject
	private lateinit var middleTaChiE: org.frice.obj.sub.ImageObject
	private lateinit var rightTaChiE: org.frice.obj.sub.ImageObject
	private lateinit var background: org.frice.obj.sub.ImageObject

	override fun onInit() {
		autoGC = false
		setBounds(100, 100, 800, 800)
		leftTaChiE = org.frice.obj.sub.ImageObject(org.frice.resource.image.ImageResource.empty())
		middleTaChiE = org.frice.obj.sub.ImageObject(org.frice.resource.image.ImageResource.empty())
		rightTaChiE = org.frice.obj.sub.ImageObject(org.frice.resource.image.ImageResource.empty())
		background = org.frice.obj.sub.ImageObject(org.frice.resource.image.ImageResource.empty())
		// do not change the order!!!!!
		addObject(background)
		addObject(leftTaChiE)
		addObject(middleTaChiE)
		addObject(rightTaChiE)
//		addObjects(createOptionButtons())
	}

	protected fun createOptionButtons(): List<org.frice.obj.button.FButton> = emptyList()

	private fun nextStep(skip: Boolean = false) {
		++step
		var recursive = false
		val now = stepSequence.first()
		when (now) {
		// change the background
			is org.frice.special.gal.GalGalBackground -> {
//				back = now.image
			}
			is org.frice.special.gal.GalGalText -> {
				TODO("print the text")
			}
			is org.frice.special.gal.GalGal立ち絵 -> {
				when (now.position) {
					org.frice.special.gal.GalCompanion.POSITION_LEFT -> {
						leftTaChiE.res = now.image
						leftTaChiE.x = width / 2.0 - now.image.image.width / 2
						leftTaChiE.y = (height - now.image.image.height).toDouble()
					}
					org.frice.special.gal.GalCompanion.POSITION_MIDDLE -> {
						middleTaChiE.res = now.image
						middleTaChiE.x = width / 4.0 - now.image.image.width / 2
						middleTaChiE.y = (height - now.image.image.height).toDouble()
					}
					org.frice.special.gal.GalCompanion.POSITION_RIGHT -> {
						rightTaChiE.res = now.image
						rightTaChiE.x = width / 4.0 * 3 - now.image.image.width / 2
						rightTaChiE.y = (height - now.image.image.height).toDouble()
					}
				}
			}
			is org.frice.special.gal.GalGalOptions -> {
				val list = listOf<org.frice.obj.button.SimpleButton>()
				TODO("show list of game options")
			}
			is org.frice.special.gal.GalGalAudio -> {
				if (!skip) play(now.file)
				stepSequence.removeAt(0)
			}
			is org.frice.special.gal.GalGalSkip -> {
				stepSequence.removeAt(0)
				recursive = true
				repeat(if (now.isAbsolute) now.index - step else now.index) { it: Int -> nextStep(true) }
			}
		}
		unless (recursive) {
			stepSequence.removeAt(0)
		}
	}

	/**
	 * init the data and start playing game
	 */
	protected fun gameStart() {
		step = 0
		nextStep()
	}

	protected fun load(path: String) = org.frice.resource.image.ImageResource.fromPath(path)

	protected fun addStep(o: org.frice.special.gal.GalStep) {
		stepSequence.add(o)
		if (o.marked) stepMarked.add(stepSequence.lastIndex)
	}

	override fun onClick(e: org.frice.event.OnClickEvent) {
		super.onClick(e)
		nextStep()
	}

	inner abstract class Step {
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
		val button: org.frice.obj.button.FButton
	}

	inner class GalTextOption(text: String, val target: Int) : org.frice.special.gal.GalGalOption {
		override val button = org.frice.obj.button.SimpleButton(
				text = text,
				x = width / 4.0,
				y = 0.0,
				width = width / 2.0,
				height = 25.0)
	}

	class GalImageOption(imageButton: org.frice.obj.button.ImageButton) : org.frice.special.gal.GalGalOption {
		override val button = imageButton
	}

	/**
	 * text
	 */
	inner class GalText(val text: String) : org.frice.special.gal.GalStep()

	/**
	 * background
	 */
	inner class GalBackground(val image: org.frice.resource.image.ImageResource) : org.frice.special.gal.GalStep()

	/**
	 * 立绘, 立ち絵
	 *
	 * @property position the position of TaChiE, should be these three values:
	 * @see POSITION_LEFT
	 * @see POSITION_MIDDLE
	 * @see POSITION_RIGHT
	 * @see GalTaChiE
	 */
	open inner class Gal立ち絵(val image: org.frice.resource.image.ImageResource, val position: Int) : org.frice.special.gal.GalStep()

	/**
	 * alias of 立ち絵
	 * @see Gal立ち絵
	 */
	inner class GalTaChiE(imageResource: org.frice.resource.image.ImageResource, position: Int) : org.frice.special.gal.GalGal立ち絵(imageResource, position)

	inner class GalOptions(val list: List<org.frice.special.gal.GalGalOption>) : org.frice.special.gal.GalStep()
	inner class GalSkip(val index: Int) : org.frice.special.gal.GalStep() {
		var isAbsolute = false
			private set

		constructor(index: Int, isAbsolute: Boolean) : this(index) {
			this.isAbsolute = isAbsolute
		}
	}

	inner class GalAudio(val file: File) : org.frice.special.gal.GalStep() {
		constructor(path: String) : this(File(path))
	}
}
