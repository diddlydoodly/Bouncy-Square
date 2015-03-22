package io.github.omgimanerd.bouncysquare.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import io.github.omgimanerd.bouncysquare.game.platform.Platform;
import io.github.omgimanerd.bouncysquare.game.platform.PlatformManager;
import io.github.omgimanerd.bouncysquare.util.Util;

public class Game {

  private static final float TILT_SENSITIVITY = 1.5f;

  private ViewPort viewPort_;
  private Square square_;
  private PlatformManager platformManager_;

  public Game() {
    viewPort_ = new ViewPort();
    square_ = new Square();
    platformManager_ = new PlatformManager();

    platformManager_.generatePlatform(0, 50, Util.SCREEN_WIDTH / 2, 0,
                                       Color.BLUE);
    platformManager_.generatePlatform(0, Util.SCREEN_HEIGHT / 2,
                                      Util.SCREEN_WIDTH / 2,
                                      Util.SCREEN_HEIGHT / 2 - 50,
                                      Color.BLUE);
  }

  public void update() {
    viewPort_.update(square_);
    square_.update(viewPort_, platformManager_.getPlatforms());
    platformManager_.update(viewPort_);
  }

  public void render(Canvas canvas) {
    square_.render(canvas);
    platformManager_.render(canvas);
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

  public void onTilt(float[] accelerations) {
    square_.setVx(-accelerations[0] * TILT_SENSITIVITY);
  }
}
