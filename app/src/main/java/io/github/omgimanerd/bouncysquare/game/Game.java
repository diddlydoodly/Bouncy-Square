package io.github.omgimanerd.bouncysquare.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import io.github.omgimanerd.bouncysquare.game.platform.PlatformManager;
import io.github.omgimanerd.bouncysquare.util.Util;

public class Game {

  private ViewPort viewPort_;
  private Square square_;
  private PlatformManager platformManager_;

  private int heightScore_;

  public Game() {
    viewPort_ = new ViewPort();
    square_ = new Square();
    platformManager_ = new PlatformManager();
    platformManager_.generateDefault();

    heightScore_ = 0;
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
