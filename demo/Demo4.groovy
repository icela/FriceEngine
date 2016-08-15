import org.frice.game.Game
import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.event.OnWindowEvent

class Demo4 extends Game {

    @Override
    protected void onInit() {

    }

    @Override
    protected void onExit() {

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

    @Override
    protected void onLoseFocus(OnWindowEvent e) {
        this.paused = true
    }

    @Override
    protected void onFocus(OnWindowEvent e) {
        this.paused = false
    }

    public static void main(String[] args) {
        new Demo4()
    }
}