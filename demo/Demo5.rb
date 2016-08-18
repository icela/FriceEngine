# This demo worked now
require 'java'

# change the import path in your own one
# don't use this path on your computer
require './FriceEngine.jar'

java_import org.frice.game.Game
java_import org.frice.game.obj.sub.ShapeObject
java_import org.frice.game.resource.graphics.ColorResource
java_import org.frice.game.utils.graphics.shape.FOval
java_import org.frice.game.utils.time.FTimer
java_import org.frice.game.utils.message.FDialog


module JavaAWT
	java_import java.awt.Rectangle do |package, name|
		'J' + name
	end
end

module JavaSwing
	include JavaAWT
end

class Demo5 < Game

	def on_init
		set_bounds JavaAWT::JRectangle.new 100, 100, 800, 800
		set_title 'JRuby demo by ice1000'

		@click_count = 0

		@bool = false

		color = ColorResource.new '111111'
		@oval = FOval.new 20.0, 15.0

		@obj = ShapeObject.new color, @oval, 20.0, 20.0
		@timer = FTimer.new 1000

		# now it's the problem. The object doesn't appear on the game window.
		add_object @obj
	end

	def on_refresh
		if @timer.ended
			if @bool
				remove_object @obj
				@bool = false
			else
				add_object @obj
				@bool = true
			end
		end
	end

	def on_click(e)
		FDialog.new(self).show "onClick #{@click_count}"
		@click_count += 1
	end

	def on_mouse(e)
	end
end

Demo5.new

# org.frice.game.Game.new