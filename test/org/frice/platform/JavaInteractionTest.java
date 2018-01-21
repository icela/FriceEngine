package org.frice.platform;

import org.frice.Game;
import org.frice.obj.AttachedObjects;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.util.shape.FRectangle;

public class JavaInteractionTest extends Game {
	@Override
	public void onInit() {
		AttachedObjects objects = new AttachedObjects();
		objects.clearDied();
		objects.clear();
		objects.add(new ShapeObject(ColorResource.BLACK, new FRectangle(100, 100)));
		objects.forEach(this::addObject);
	}
}
