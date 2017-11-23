package org.frice.utils.message

import java.awt.Frame
import javax.swing.JOptionPane

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.2
 */
class FDialog @JvmOverloads constructor(val game: Frame? = null) {
	fun show(msg: String) = JOptionPane.showMessageDialog(game, msg)
	fun input() = input("")
	fun input(msg: String): String = JOptionPane.showInputDialog(game, msg)
	fun confirm(msg: String) = confirm(msg, "")
	fun confirm(msg: String, title: String) = confirm(msg, title, JOptionPane.YES_NO_CANCEL_OPTION)
	fun confirm(msg: String, title: String, option: Int) = JOptionPane.showConfirmDialog(game, msg, title, option)

	companion object Flags {
		const val YES_NO_OPTION = 0
		const val YES_NO_CANCEL_OPTION = 1
		const val OK_CANCEL_OPTION = 2
		const val YES_OPTION = 0
		const val NO_OPTION = 1
		const val CANCEL_OPTION = 2
		const val OK_OPTION = 0
		const val CLOSED_OPTION = -1
		const val ERROR_MESSAGE = 0
		const val INFORMATION_MESSAGE = 1
		const val WARNING_MESSAGE = 2
		const val QUESTION_MESSAGE = 3
		const val PLAIN_MESSAGE = -1
	}
}