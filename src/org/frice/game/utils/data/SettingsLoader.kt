package org.frice.game.utils.data

import org.frice.game.Game
import org.frice.game.utils.kotlin.forceGet
import org.frice.game.utils.message.log.FLog
import java.io.File

/**
 * Load settings from a properties file.
 * Keys are field names in
 * Created by ice1000 on 2016/8/20.
 *
 * @author ice1000
 * @since v0.4.1
 */
class SettingsLoader(file: File) {
	constructor(path: String) : this(File(path))

	private val p = Preference(file)

	fun load(game: Game) {
		var clazz: Class<*> = game.javaClass
		p.list().forEach { pair ->
			while (true) try {
				val field = clazz.superclass.getDeclaredField(pair.first as String)
				field.isAccessible = true
				field.set(game, when (pair.second) {
					"true" -> true
					"false" -> false
					else -> forceGet(pair.second) {
						Integer.parseInt(pair.second as String)
					}
				})
				break
			} catch (e: Exception) {
				clazz = clazz.superclass
				FLog.d("$clazz\n")
				continue
			}
		}
	}
}