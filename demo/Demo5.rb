# This demo doesn't work.
# I expect to extend a java class but it seemed not working.
# Is this the problem of my JRuby environment?

require 'java'

# change the import path in your own one
# don't use this path on your computer
require 'FriceEngine.jar'

class Demo5 < org.frice.game.Game
	def onInit
		setBounds java.awt.Rectangle.new 100, 100, 800, 800
		setTitle 'JRuby demo by ice1000'

		@bool = false

		@oval = org.frice.game.utils.shape.FOval.new 20.0, 15.0
		@obj = org.frice.game.obj.ShapeObject.new org.frice.game.resource.ColorResource.Companion.BLUE, @oval
		@timer = org.frice.game.utils.time.FTimer.new 1000
	end

	def on_refresh
		if @timer.ended
			if @bool
				remove_object @obj
			else
				add_object @obj
			end
		end
	end

	def on_click(e)
	end

	def on_mouse(e)
	end
end

Demo5.new

org.frice.game.Game.new