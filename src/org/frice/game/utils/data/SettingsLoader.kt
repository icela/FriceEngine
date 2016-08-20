//package org.frice.game.utils.data
//
//import java.io.File
//
///**
// * Load settings from a properties file.
// * Keys are field names in
// * Created by ice1000 on 2016/8/20.
// *
// * @deprecated
// * @author ice1000
// * @since v0.4.1
// */
//class SettingsLoader(file: File) {
//	constructor(path: String) : this(File(path))
//
////	private val p = Preference(file)
////
////	fun load(game: Game) {
////		var clazz: Class<*> = game.javaClass
////		p.list().forEach { pair ->
////			while (true) try {
////				FLog.d(clazz.toString().substring(6) + "." + pair.first as String)
////				val field = clazz.superclass.getDeclaredField(clazz.toString().substring(6) + "." + pair.first as String)
////				field.isAccessible = true
////				field.set(game, when (pair.second) {
////					"true" -> true
////					"false" -> false
////					else -> forceGet(pair.second) {
////						Integer.parseInt(pair.second as String)
////					}
////				})
////				break
////			} catch (e: Exception) {
////				clazz = clazz.superclass
////				FLog.d("$clazz\n")
////				continue
////			}
////		}
////	}
//}