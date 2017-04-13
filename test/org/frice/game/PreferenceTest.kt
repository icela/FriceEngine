package org.frice.game

import org.frice.game.utils.data.Preference
import org.frice.game.utils.message.log.FLog
import org.junit.Test

/**
 * Created by ice1000 on 2016/8/21.
 * @author ice1000
 * @since v0.4.1
 */
class PreferenceTest {

	private val p = Preference("Fuck.properties")

	@Test
	fun query() {
		p.insert("fuck", 2333)
	}

	@Test
	fun insert() {
		FLog.i(p.query("fuck", 6666))
	}
}