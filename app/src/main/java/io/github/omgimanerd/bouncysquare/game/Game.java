package io.github.omgimanerd.bouncysquare.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import io.github.omgimanerd.bouncysquare.game.platform.PlatformManager;
import io.github.omgimanerd.bouncysquare.util.CustomResources;
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

    /**
     * A new platform is generated as soon as an old one is removed.
     * PlatformManager will automatically take care of spacing them evenly
     * apart. By manually declaring these four platforms,
     * we also ensure that there will only be four platforms in existence at
     * any given point.
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
        Util.SCREEN_HEIGHT / 3, CustomResources.selectRandomColor());
    platformManager_.generatePlatform(
        Util.SCREEN_WIDTH / 3,
        Util.SCREEN_HEIGHT * 2 / 3 + PlatformManager.PLATFORM_HEIGHT,
        Util.SCREEN_WIDTH / 3 + PlatformManager.PLATFORM_LENGTH,
        Util.SCREEN_HEIGHT * 2 / 3, CustomResources.selectRandomColor());
    platformManager_.generatePlatform(
        Util.SCREEN_WIDTH * 2 / 3,
        Util.SCREEN_HEIGHT + PlatformManager.PLATFORM_HEIGHT,
        Util.SCREEN_WIDTH * 2 / 3 + PlatformManager.PLATFORM_LENGTH,
        Util.SCREEN_HEIGHT, CustomResources.selectRandomColor());

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
