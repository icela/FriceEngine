package org.frice.game.special.gal

import org.frice.game.Game
import org.frice.game.event.OnClickEvent
import org.frice.game.obj.button.FButton
import org.frice.game.obj.button.ImageButton
import org.frice.game.obj.button.SimpleButton
import org.frice.game.obj.sub.ImageObject
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.audio.AudioManager
import org.frice.game.utils.misc.loop
import java.io.File
import java.util.*

/**
 * Easy framework to help you make a galgame.
 * Created by ice1000 on 2016/9/3 0003.
 *
 * @author ice1000
 * @since v0.5
 */
internal class GalGame : Game() {

	companion object {
		@JvmField val POSITION_LEFT = 0
		@JvmField val POSITION_MIDDLE = 1
		@JvmField val POSITION_RIGHT = 2
	}

	private val stepSequence = ArrayList<Step>()
	private val stepMarked = ArrayList<Int>()

	protected var step = 0
		private set

	private lateinit var leftTaChiE: ImageObject
	private lateinit var middleTaChiE: ImageObject
	private lateinit var rightTaChiE: ImageObject
	private lateinit var background: ImageObject

	override fun onInit() {
		autoGC = false
		setBounds(100, 100, 800, 800)
		leftTaChiE = ImageObject(ImageResource.empty())
		middleTaChiE = ImageObject(ImageResource.empty())
		rightTaChiE = ImageObject(ImageResource.empty())
		background = ImageObject(ImageResource.empty())
		// do not change the order!!!!!
		addObject(background)
		addObject(leftTaChiE)
		addObject(middleTaChiE)
		addObject(rightTaChiE)
//		addObjects(createOptionButtons())
	}

	protected fun createOptionButtons(): List<FButton> = emptyList()

	private fun nextStep(skip: Boolean = false) {
		++step
		var recursive = false
		val now = stepSequence.first()
		when (now) {
		// change the background
			is GalBackground -> {
//				back = now.image
			}
			is GalText -> {
				TODO("print the text")
			}
			is Gal立ち絵 -> {
				when (now.position) {
					POSITION_LEFT -> {
						leftTaChiE.res = now.image
						leftTaChiE.x = width / 2.0 - now.image.image.width / 2
						leftTaChiE.y = (height - now.image.image.height).toDouble()
					}
					POSITION_MIDDLE -> {
						middleTaChiE.res = now.image
						middleTaChiE.x = width / 4.0 - now.image.image.width / 2
						middleTaChiE.y = (height - now.image.image.height).toDouble()
					}
					POSITION_RIGHT -> {
						rightTaChiE.res = now.image
						rightTaChiE.x = width / 4.0 * 3 - now.image.image.width / 2
						rightTaChiE.y = (height - now.image.image.height).toDouble()
					}
				}
			}
			is GalOptions -> {
				val list = listOf<SimpleButton>()
				TODO("show list of game options")
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

	protected fun addStep(o: Step) {
		stepSequence.add(o)
		if (o.marked) stepMarked.add(stepSequence.lastIndex)
	}

	override fun onClick(e: OnClickEvent) {
		super.onClick(e)
		nextStep()
	}

	inner abstract class Step() {
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
	inner class GalText(val text: String) : Step()

	/**
	 * background
	 */
	inner class GalBackground(val image: ImageResource) : Step()

	/**
	 * 立绘, 立ち絵
	 *
	 * @property position the position of TaChiE, should be these three values:
	 * @see POSITION_LEFT
	 * @see POSITION_MIDDLE
	 * @see POSITION_RIGHT
	 * @see GalTaChiE
	 */
	open inner class Gal立ち絵(val image: ImageResource, val position: Int) : Step()

	/**
	 * alias of 立ち絵
	 * @see Gal立ち絵
	 */
	inner class GalTaChiE(imageResource: ImageResource, position: Int) : Gal立ち絵(imageResource, position)

	inner class GalOptions(val list: List<GalOption>) : Step()
	inner class GalSkip(val index: Int) : Step() {
		var isAbsolute = false
			private set

		constructor(index: Int, isAbsolute: Boolean) : this(index) {
			this.isAbsolute = isAbsolute
		}
	}

	inner class GalAudio(val file: File) : Step() {
		constructor(path: String) : this(File(path))
	}
}