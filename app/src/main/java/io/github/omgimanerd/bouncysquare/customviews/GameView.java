package io.github.omgimanerd.bouncysquare.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import io.github.omgimanerd.bouncysquare.game.Game;
import io.github.omgimanerd.bouncysquare.game.ViewPort;

public class GameView extends View {

  private Game game_;

  public GameView(Context context, AttributeSet attrs) {
    super(context, attrs);

    game_ = new Game();
  }

  public void onDraw(Canvas canvas) {
    game_.update();
    game_.render(canvas);

    invalidate();
  }

  public boolean onTouchEvent(MotionEvent event) {
    game_.onTouch(event);
    return true;
  }
}
