package org.frice.game.utils.data

import org.frice.game.Game
import org.frice.game.event.OnClickEvent
import org.frice.game.utils.data.Preference
import org.frice.game.utils.data.SettingsLoader
import org.frice.game.utils.message.log.FLog

/**
 * Created by ice1000 on 2016/8/21.
 *
 * @author ice1000
 */

class FFF() : Game() {

	private val file = "settings.properties"
	private val loader = SettingsLoader(file)
	private var started = false

	fun load() {
//		this.javaClass.superclass.declaredFields.forEach {
//			FLog.w(it)
//		}
		Preference(file).apply {
			insert("autoGC", "false")
			insert("title", "settings test title set")
			insert("started", "false")
		}
		loader.load(this)
	}

	override fun onInit() {
//		load()
//		loader.load(this)
		title = "gjstbtk"
		FLog.d(title)
	}

	override fun onRefresh() {
//		super.onRefresh()
//		title = "233333"
//		if (!started) {
//		}
	}

	override fun onClick(e: OnClickEvent?) {
		super.onClick(e)
		started = true
		load()
		print("title = $title, started = $started")
	}
}

fun main(args: Array<String>) {
	FFF()
}