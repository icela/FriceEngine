package org.frice.game.demo;

import kotlin.Unit;
import org.frice.game.Game;
import org.frice.game.anim.move.AccurateMove;
import org.frice.game.obj.button.FText;
import org.frice.game.obj.button.ImageButton;
import org.frice.game.obj.button.SimpleText;
import org.frice.game.obj.sub.ImageObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.resource.graphics.ParticleResource;
import org.frice.game.resource.image.ImageResource;
import org.frice.game.utils.data.Database;
import org.frice.game.utils.data.Preference;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/9/15 0015.
 *
 * @author ice1000
 */
public class Java extends Game {
	private static final String URL = "https://coding.net/u/ice1000/p/Images/git/raw/master/blog-img/13/a.png";

	@Override
	public void onInit() {
		super.onInit();
		ImageButton imageButton = new ImageButton(
				ImageResource.fromWeb("http://img.blog.csdn.net/20151123175207052"),
				ImageResource.fromWeb("http://img.blog.csdn.net/20151123175205567"),
				50, 50);
		ImageObject imageObject = new ImageObject(ImageResource.fromWeb(URL), -90, 100);
		ImageObject imageObject1 = new ImageObject(ImageResource.fromWeb(URL), -20, 200);
		ImageObject imageObject2 = new ImageObject(ImageResource.fromWeb(URL), 300, 100);
		SimpleText text = new SimpleText(ColorResource.BLACK, "暗中观察.jpg", 50, 200);
		imageObject.addAnim(new AccurateMove(50, 0));
		imageObject.addCollider(imageObject2, () -> {
			imageObject.stopAnims();
			return Unit.INSTANCE;
		});
		addObject(text, imageObject, imageObject2, imageObject1);
		Database database = new Preference("save.txt");
		boolean[] booleans = new boolean[]{true, true, true, true, false, true};
		database.insert("TheData", makeString(booleans));
		boolean[] result = makeBoolean(database.query("TheData", "").toString().toCharArray());
		System.out.println(Arrays.toString(result));
//		addObject(imageButton);
	}

	private String makeString(boolean[] booleans) {
		char[] chars = new char[booleans.length];
		for (int i = 0; i < booleans.length; i++) chars[i] = booleans[i] ? '1' : '0';
		return new String(chars);
	}

	private boolean[] makeBoolean(char[] chars) {
		boolean[] booleans = new boolean[chars.length];
		for (int i = 0; i < chars.length; i++) booleans[i] = chars[i] == '1';
		return booleans;
	}

	@Override
	public void onExit() {
		System.exit(0);
	}

	public static void main(String[] args) {
		new Java();
	}
}
