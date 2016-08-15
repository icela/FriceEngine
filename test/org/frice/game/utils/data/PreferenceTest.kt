package org.frice.game.utils.data

import org.junit.Test

/**
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.2
 */
class PreferenceTest {
	@Test
	fun insert() {
		val p = Preference("text.xml")
		p.insert("ice", 1000)
	}
}