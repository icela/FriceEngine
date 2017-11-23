package org.frice.utils.data

import org.frice.utils.misc.forceGet
import org.frice.utils.misc.forceRun
import java.io.File
import java.util.*

/**
 * Created by ice1000 on 2016/8/20.
 *
 * @author ice1000
 * @since v0.4.1
 */
class Preference(private val file: File) : Database {

	constructor(path: String) : this(File(path))

	private val properties: Properties = Properties()

	init {
		if (!file.exists()) file.createNewFile()
		forceRun { properties.load(file.inputStream()) }
	}

	override fun query(key: String, default: Any?): Any? = when (properties[key].toString()) {
		"true" -> true
		"false" -> false
		else -> forceGet(properties[key] ?: default) {
			return@forceGet Integer.parseInt(properties[key] as String)
		}
	}

	override fun insert(key: String, value: Any?) {
		properties.put(key, value.toString())
		properties.store(file.outputStream(), "Automatically generated by ice1000 Frice Engine")
	}

	fun list(): List<Pair<Any, Any>> = properties.toList()
}