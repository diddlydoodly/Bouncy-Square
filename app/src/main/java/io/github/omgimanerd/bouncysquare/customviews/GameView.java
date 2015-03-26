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

  public GameView(Context context, AttributeSet attrs) {
    super(context, attrs);

    parentActivity_ = (GameActivity) context;
    lastUpdateTime_ = currentTimeMillis();
    game_ = new Game();
  }

  public void onDraw(Canvas canvas) {
    if (currentTimeMillis() > lastUpdateTime_ + (1000 / FPS)) {
      game_.update();
      game_.render(canvas);
      parentActivity_.updateScoreView(game_.getScore());
    }
    if (game_.isLost()) {
      parentActivity_.showLostOverlay(game_.getScore());
    }
    invalidate();
  }

  public boolean onTouchEvent(MotionEvent event) {
    game_.onTouch(event);
    return super.onTouchEvent(event);
  }
}
