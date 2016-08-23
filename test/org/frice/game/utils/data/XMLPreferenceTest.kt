package org.frice.game.utils.data

import org.junit.Test

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */
class XMLPreferenceTest {

	lateinit var p: XMLPreference

	fun init() {
		p = XMLPreference("D://text.xml")
	}

	@Test
	fun insert() {
		init()
		p.insert("ice", 1000)
		p.insert("lizhaohan", 1)
		p.insert("jelly", "bean")
	}

	@Test
	fun query() {
		init()
		println("p?.query(\"ice\", 5) = ${p.query("ice", 5)}")
		println("p?.query(\"jelly\", jelly) = ${p.query("jelly", "")}")
	}
}