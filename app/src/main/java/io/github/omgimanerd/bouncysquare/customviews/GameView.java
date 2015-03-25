package io.github.omgimanerd.bouncysquare.customviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import io.github.omgimanerd.bouncysquare.GameActivity;
import io.github.omgimanerd.bouncysquare.game.Game;

import static java.lang.System.currentTimeMillis;

public class GameView extends View {

  private static final int FPS = 60;

  private long lastUpdateTime_;
  private Game game_;

  private SharedPreferences persistentData_;

  public GameView(Context context, AttributeSet attrs) {
    super(context, attrs);

    lastUpdateTime_ = currentTimeMillis();
    game_ = new Game(context);

    persistentData_ = context.getSharedPreferences("bouncy_square", 0);
  }

  public void onDraw(Canvas canvas) {
    if (currentTimeMillis() > lastUpdateTime_ + (1000 / FPS)) {
      game_.update();
      game_.render(canvas);
    }
    if (game_.isLost()) {
      int highScore = persistentData_.getInt("bouncy_square_highscore", 0);
      if (game_.getScore() > highScore) {
        SharedPreferences.Editor editor = persistentData_.edit();
        editor.putInt("bouncy_square_highscore", game_.getScore());
        editor.commit();
      }
      // Bootleggy upward reference.
      ((GameActivity) getContext()).showLostOverlay(game_.getScore(), highScore);
    }
    invalidate();
  }

  public boolean onTouchEvent(MotionEvent event) {
    game_.onTouch(event);
    return super.onTouchEvent(event);
  }
}
