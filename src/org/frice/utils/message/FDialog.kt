package org.frice.utils.message

import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import java.awt.Frame
import javax.swing.JOptionPane

@Deprecated("Please use methods from `Game`")
abstract class FriceDialog {
	fun input() = input("")
	abstract fun input(msg: String): String
}

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.2
 * @deprecated
 */
@Deprecated("Please use methods from `Game`", level = DeprecationLevel.ERROR)
class FDialog @JvmOverloads constructor(val game: Frame? = null) : FriceDialog() {
	fun show(msg: String) = JOptionPane.showMessageDialog(game, msg)

	override fun input(msg: String): String =
		JOptionPane.showInputDialog(game, msg)

	@JvmOverloads
	fun confirm(msg: String, title: String = "", option: Int = YES_NO_CANCEL_OPTION) =
		JOptionPane.showConfirmDialog(game, msg, title, option)
}

/**
 * @author ice1000
 * @since v1.6.0
 */
@Deprecated("Please use methods from `GameFX`")
object FDialogFX : FriceDialog() {
	@JvmStatic
	fun confirm(msg: String, type: AlertType = AlertType.CONFIRMATION, vararg buttonType: ButtonType): ButtonType =
		Alert(type, msg, *buttonType).let {
			it.isResizable = false
			it.showAndWait()
			return@let it.result
		}

	override fun input(default: String): String = TextInputDialog(default).let {
		it.isResizable = false
		it.showAndWait()
		return@let it.result
	}
}

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