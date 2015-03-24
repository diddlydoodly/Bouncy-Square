package io.github.omgimanerd.bouncysquare.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import io.github.omgimanerd.bouncysquare.game.platform.PlatformManager;
import io.github.omgimanerd.bouncysquare.util.Util;

public class Game {

  private ViewPort viewPort_;
  private Square square_;
  private PlatformManager platformManager_;

  public Game() {
    viewPort_ = new ViewPort();
    square_ = new Square();
    platformManager_ = new PlatformManager();

    platformManager_.generatePlatform(0, 20, Util.SCREEN_WIDTH, 0, Color.BLACK);

    platformManager_.generatePlatform(0, Util.SCREEN_HEIGHT / 2,
                                      Util.SCREEN_WIDTH / 3,
                                      Util.SCREEN_HEIGHT / 2 - 50,
                                      Color.BLUE);
  }

  public void update() {
    viewPort_.update(square_);
    square_.update(viewPort_, platformManager_.getPlatforms());
    platformManager_.update(viewPort_);
    if (platformManager_.getPlatforms().size() < 3) {
      platformManager_.generateRandomPlatform(viewPort_);
    }
  }

  public void render(Canvas canvas) {
    square_.render(canvas);
    platformManager_.render(canvas);
  }

  public boolean isLost() {
    if (!viewPort_.isVisible(square_.getSquare())) {
      return true;
    }
    return false;
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
}
