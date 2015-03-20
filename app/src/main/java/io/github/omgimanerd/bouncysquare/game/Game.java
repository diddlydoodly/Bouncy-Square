package io.github.omgimanerd.bouncysquare.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import io.github.omgimanerd.bouncysquare.game.platform.Platform;
import io.github.omgimanerd.bouncysquare.util.Util;

public class Game {

  private ViewPort viewPort_;
  private Square square_;
  private ArrayList<Platform> platforms_;

  public Game() {
    viewPort_ = new ViewPort();
    square_ = new Square();
    platforms_ = new ArrayList<>();
  }

  public void update() {
    viewPort_.update(square_);
    square_.update(viewPort_, platforms_);
    for (Platform platform : platforms_) {
      platform.update(viewPort_);
    }
  }

  public void render(Canvas canvas) {
    square_.render(canvas);
    for (Platform platform : platforms_) {
      platform.render(canvas);
    }
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
