import org.frice.game.Game;
import org.frice.game.obj.ImageObject;
import org.frice.game.resource.image.FrameImageResource;
import org.frice.game.resource.image.WebImageResource;

/**
 * Created by ice1000 on 2016/8/16.
 *
 * @author ice1000
 * @since v0.3.1
 */
public class Demo8 extends Game {
	private ImageObject object;

	@Override
	protected void onInit() {
		object = new ImageObject(new FrameImageResource(new WebImageResource[]{
				new WebImageResource("1.png"),
				new WebImageResource("2.png"),
				new WebImageResource("3.png"),
				new WebImageResource("4.png"),
				new WebImageResource("5.png")}, 1000), 100.0, 100.0);
		addObject(object);
	}

	@Override
	protected void onRefresh() {
	}

	public static void main(String[] args) {
		new Demo8();
	}
}
