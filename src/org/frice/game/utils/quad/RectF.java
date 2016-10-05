package org.frice.game.utils.quad;

/**
 * Rect of QuadTree
 * Created by liufengkai on 2016/10/4.
 */
public class RectF {

	public double left, top, w, h;


	public RectF(double x, double y, double w, double h) {
		this.left = x;
		this.top = y;
		this.w = w;
		this.h = h;
	}

	public double height() {
		return h;
	}

	public double width() {
		return w;
	}
}
