package io.github.omgimanerd.bouncysquare.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import io.github.omgimanerd.bouncysquare.R;
import io.github.omgimanerd.bouncysquare.game.platform.Platform;
import io.github.omgimanerd.bouncysquare.game.platform.PlatformManager;
import io.github.omgimanerd.bouncysquare.util.Util;

public class Game {

  public static int[] STANDARD_COLORS = new int[4];

  private ViewPort viewPort_;
  private Square square_;
  private PlatformManager platformManager_;

  private int heightScore_;
  private Paint scoreTextPaint_;

  public Game(Context context) {
    Resources res = context.getResources();
    STANDARD_COLORS[0] = res.getColor(R.color.STANDARD_RED);
    STANDARD_COLORS[1] = res.getColor(R.color.STANDARD_BLUE);
    STANDARD_COLORS[2] = res.getColor(R.color.STANDARD_GREEN);
    STANDARD_COLORS[3] = res.getColor(R.color.STANDARD_YELLOW);

    viewPort_ = new ViewPort();
    square_ = new Square();
    platformManager_ = new PlatformManager();

    /**
     * A new platform is generated as soon as an old one is removed,
     * therefore these platforms are spaced thirds apart so that new
     * platforms generated will be automatically spaced evenly.
     */
    platformManager_.generatePlatform(
        0,
        PlatformManager.PLATFORM_HEIGHT,
        Util.SCREEN_WIDTH,
        0,
        Color.BLACK);
    platformManager_.generatePlatform(
        0,
        Util.SCREEN_HEIGHT / 3 + PlatformManager.PLATFORM_HEIGHT,
        PlatformManager.PLATFORM_LENGTH,
        Util.SCREEN_HEIGHT / 3,
        Game.STANDARD_COLORS[(int) (Math.random() * 4)]);
    platformManager_.generatePlatform(
        Util.SCREEN_WIDTH * 2 / 3,
        Util.SCREEN_HEIGHT * 2 / 3 + PlatformManager.PLATFORM_HEIGHT,
        Util.SCREEN_WIDTH * 2 / 3 + PlatformManager.PLATFORM_LENGTH,
        Util.SCREEN_HEIGHT * 2 / 3,
        Game.STANDARD_COLORS[(int) (Math.random() * 4)]);

    heightScore_ = 0;
    scoreTextPaint_ = new Paint();
    scoreTextPaint_.setColor(Color.BLACK);
    scoreTextPaint_.setTextSize(res.getDimensionPixelSize(
        R.dimen.SCORE_TEXT_SIZE));
  }

  public void update() {
    if (!isLost()) {
      viewPort_.update(square_);
      square_.update(viewPort_, platformManager_.getPlatforms());
      platformManager_.update(viewPort_);

      if (square_.getSquare().top > heightScore_) {
        heightScore_ = (int) square_.getSquare().top;
      }
    }
  }

  public void render(Canvas canvas) {
    square_.render(canvas);
    platformManager_.render(canvas);

    canvas.drawText(heightScore_ + "",
                    scoreTextPaint_.getTextSize(),
                    scoreTextPaint_.getTextSize(),
                    scoreTextPaint_);
  }

  public boolean isLost() {
    return square_.isLost();
  }

  public void onTouch(MotionEvent event) {
    int action = event.getAction();

    switch (action) {
      case MotionEvent.ACTION_DOWN:
        if (event.getX() > Util.SCREEN_WIDTH / 2) {
          square_.rotateClockwise();
        } else {
          square_.rotateCounterClockwise();
        }
        break;
      default:
        break;
    }
  }

  public int getScore() {
    return heightScore_;
  }
}
