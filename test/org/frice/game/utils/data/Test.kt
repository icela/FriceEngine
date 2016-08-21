package org.frice.game.utils.data

import org.frice.game.Game
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.message.log.FLog

/**
 * Created by ice1000 on 2016/8/21.
 *
 * @author ice1000
 */
class Test() : Game() {
	override fun onInit() {
		super.onInit()
		FLog.i(FDialog(this).confirm("fuck"))
	}

//	override fun onExit() {
//		System.exit(0)
//	}
}

fun main(args: Array<String>) {
	Test()
}