import org.frice.game.Game;
import org.frice.game.anim.scale.SimpleScale;
import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnMouseEvent;
import org.frice.game.obj.ShapeObject;
import org.frice.game.resource.ColorResource;
import org.frice.game.utils.shape.FOval;

/**
 * Created by ice1000 on 2016/8/15.
 *
 * @author ice1000
 */
public class Demo6 extends Game {
	public static void main(String[] args) {
		new Demo6();
	}

	@Override
	protected void onInit() {
		setSize(800, 800);
		addObject(new ShapeObject(ColorResource.Companion.get基佬紫(), new FOval(80.0, 180.0), 100, 100) {{
			getAnims().add(new SimpleScale(1.5, 1.5));
		}});
	}

	@Override
	protected void onRefresh() {

	}

	@Override
	protected void onClick(OnClickEvent e) {

	}

	@Override
	protected void onMouse(OnMouseEvent e) {

	}
}
