package org.frice.game;

import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnMouseEvent;

/**
 * Created by ice1000 on 2016/8/15.
 *
 * @author ice1000
 * @since v0.2.3
 */
public abstract class GameForRuby {
	protected Game window;

	public GameForRuby() {
	}

	protected void initWindow() {
		window = new Game() {
			@Override
			protected void onInit() {
				GameForRuby.this.onInit();
			}

			@Override
			protected void onRefresh() {
				GameForRuby.this.onRefresh();
			}

			@Override
			protected void onClick(OnClickEvent e) {
				GameForRuby.this.onClick(e);
			}

			@Override
			protected void onMouse(OnMouseEvent e) {
				GameForRuby.this.onMouse(e);
			}
		};
	}

	protected abstract void onInit();

	protected abstract void onRefresh();

	abstract void onClick(OnClickEvent e);

	abstract void onMouse(OnMouseEvent e);
}
