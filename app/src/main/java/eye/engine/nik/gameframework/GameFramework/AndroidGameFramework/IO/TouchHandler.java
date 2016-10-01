package eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.IO;

import java.util.List;
import android.view.View.OnTouchListener;

import eye.engine.nik.gameframework.GameFramework.IO.Input;

public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<Input.TouchEvent> getTouchEvents();
}