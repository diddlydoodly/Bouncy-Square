package io.github.omgimanerd.bouncysquare.customviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import io.github.omgimanerd.bouncysquare.GameActivity;
import io.github.omgimanerd.bouncysquare.game.Game;

import static java.lang.System.currentTimeMillis;

public class GameView extends View {

  private static final int FPS = 60;

  private GameActivity parentActivity_;
  private long lastUpdateTime_;
  private Game game_;
  private boolean paused_;

  public GameView(Context context, AttributeSet attrs) {
    super(context, attrs);

    parentActivity_ = (GameActivity) context;
    lastUpdateTime_ = currentTimeMillis();
    game_ = new Game();
    paused_ = false;
  }

  public void onDraw(Canvas canvas) {
    // Game loop interpolation, this logic here ensures a quasi-constant FPS.
    if (currentTimeMillis() > lastUpdateTime_ + (1000 / FPS) && !paused_) {
      game_.update();
      parentActivity_.updateScoreView(game_.getScore());
    }

    game_.render(canvas);

    if (game_.isLost()) {
      parentActivity_.showLostOverlay(game_.getScore());
    }
    invalidate();
  }

  public void pause() {
    paused_ = !paused_;
  }

  public boolean onTouchEvent(MotionEvent event) {
    game_.onTouch(event);
    return super.onTouchEvent(event);
  }
}
