# This demo worked now
require 'java'

# change the import path in your own one
# don't use this path on your computer
require './FriceEngine.jar'

class Demo5 < org.frice.game.Game
	def onInit
		setBounds java.awt.Rectangle.new 100, 100, 800, 800
		setTitle 'JRuby demo by ice1000'

		@bool = false

		color = org.frice.game.resource.ColorResource.new '111111'
		@oval = org.frice.game.utils.graphics.shape.FOval.new 20.0, 15.0

		@obj = org.frice.game.obj.ShapeObject.new color, @oval, 20.0, 20.0
		@timer = org.frice.game.utils.time.FTimer.new 1000

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
		org.frice.game.utils.message.FDialog.new(self).show 'onClick'
	end

	def on_mouse(e)
	end
end

Demo5.new

# org.frice.game.Game.new