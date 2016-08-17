import org.frice.game.Game
import org.frice.game.event.OnClickEvent
import org.frice.game.resource.image.FileImageResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.data.Preference
import org.frice.game.utils.message.FDialog
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.awt.image.BufferedImage

/**
 * Groovy sample
 *
 * Demo for database operating
 */
class Demo4 extends Game {

	def fuck = Preference.getPreference("test.xml")
	def FDialog dialog
	def closed = false

	def image = new FileImageResource("1.png")

	@Override
	def void onInit() {
		dialog = new FDialog(this)

		/*
		 * Groovy and Kotlin
		 */
		setCursor(new ImageResource() {
			@Override
			BufferedImage getImage() {
				return Demo4.this.image.image
			}

			@Override
			void setImage(@NotNull BufferedImage bufferedImage) {
				Demo4.this.image.image = bufferedImage
			}
		})

	}

	@Override
	def void onRefresh() {
		if (!closed) switch (dialog.confirm("what do U want?")) {
			case 0:
				fuck.insert(
						dialog.input("inserting, key?"),
						dialog.input("inserting, value?"))
				break
			case 1:
				dialog.confirm(fuck.query(
						dialog.input("querying, key?"),
						dialog.input("querying, value?")).toString())
				break
			case 2:
				closed = true
				break
		}
	}

	@Override
	def void onClick(@Nullable OnClickEvent e) {
		// Do something
	}

	def static void main(String[] args) {
		new Demo4()
	}
}