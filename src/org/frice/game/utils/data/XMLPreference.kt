package org.frice.game.utils.data

import org.frice.game.utils.kotlin.forceGet
import org.frice.game.utils.kotlin.forceLoop
import org.frice.game.utils.message.error.FatalError
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * An Android-like XMLPreference.
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since 0.2.2
 */
class XMLPreference constructor(val file: File) : Database{
	constructor(path: String) : this(File(path))

	private val builder: DocumentBuilder
	private val doc: Document
	private val root: Element

	companion object {
		private val ROOT = "PREFERENCE_CONST_ROOT"
		private val TYPE = "PREFERENCE_CONST_TYPE"
		private val VALUE = "PREFERENCE_CONST_VALUE"

		private val TYPE_BYTE = "PREFERENCE_CONST_TYPE_BYTE"
		private val TYPE_INT = "PREFERENCE_CONST_TYPE_INT"
		private val TYPE_LONG = "PREFERENCE_CONST_TYPE_LONG"
		private val TYPE_SHORT = "PREFERENCE_CONST_TYPE_SHORT"
		private val TYPE_DOUBLE = "PREFERENCE_CONST_TYPE_DOUBLE"
		private val TYPE_FLOAT = "PREFERENCE_CONST_TYPE_FLOAT"
		private val TYPE_STRING = "PREFERENCE_CONST_TYPE_STRING"
		private val TYPE_CHAR = "PREFERENCE_CONST_TYPE_CHAR"

		private var instance: XMLPreference? = null

		@JvmStatic fun getPreference(path: String) = getPreference(File(path))

		@JvmStatic fun getPreference(file: File): XMLPreference {
			if (instance == null) instance = XMLPreference(file)
			return instance!!
		}
	}

	init {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
		if (!file.exists()) {
			file.createNewFile()
			doc = builder.newDocument()
			root = doc.createElement(ROOT)
			doc.appendChild(root)
			save()
		} else {
			doc = builder.parse(file)
			root = doc.documentElement
		}
	}

	private fun save() {
		val transformer = TransformerFactory.newInstance().newTransformer()
		transformer.transform(DOMSource(doc), StreamResult(file))
	}

	override fun insert(key: String, value: Any?) = value.let {
		forceLoop { root.removeChild(doc.getElementsByTagName(key).item(0)) }
		val node = doc.createElement(key)
		node.setAttribute(VALUE, value.toString())
		node.setAttribute(TYPE, when (value) {
			is Byte -> TYPE_BYTE
			is Int -> TYPE_INT
			is Long -> TYPE_LONG
			is Short -> TYPE_SHORT
			is Float -> TYPE_FLOAT
			is Double -> TYPE_DOUBLE
			is Char -> TYPE_CHAR
			is String -> TYPE_STRING
			else -> throw FatalError("invalid type!")
		})
		root.appendChild(node)
		save()
	}

//	override fun <T> queryWithType(key: String, default: T): Any {
//		val value = doc.getElementsByTagName(key).item(0).attributes.getNamedItem(VALUE).nodeValue
//		return value ?: default!!
//	}

	override fun query(key: String, default: Any): Any {
		val node = doc.getElementsByTagName(key).item(0)
		val value: String?
		try {
			value = node.attributes.getNamedItem(VALUE).nodeValue
		} catch (e: Throwable) {
			return default
		}
		return forceGet(default) {
			when (node.attributes.getNamedItem(TYPE).nodeValue) {
				TYPE_BYTE -> value.toByte()
				TYPE_INT -> value.toInt()
				TYPE_LONG -> value.toLong()
				TYPE_SHORT -> value.toShort()
				TYPE_CHAR -> value.toCharArray()[0]
				TYPE_FLOAT -> value.toFloat()
				TYPE_DOUBLE -> value.toDouble()
				TYPE_STRING -> value
				else -> default
			}
		}
	}
}