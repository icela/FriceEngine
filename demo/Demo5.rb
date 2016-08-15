require 'java'

class Demo5 < org.frice.game.GameForRuby
	def initialize
		onInit
	end
	def onInit
		initWindow
		window.setBounds java.awt.Rectangle.new 100, 100, 800, 800
		window.setTitle 'JRuby demo by ice1000'
	end
	def on_refresh
	end
	def on_click(e)
	end
	def on_mouse(e)
	end
end

Demo5.new

org.frice.game.Game.new