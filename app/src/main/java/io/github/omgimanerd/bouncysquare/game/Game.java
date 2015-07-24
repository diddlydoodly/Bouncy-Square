package io.github.omgimanerd.bouncysquare.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import io.github.omgimanerd.bouncysquare.game.background.BackgroundManager;
import io.github.omgimanerd.bouncysquare.game.platform.PlatformManager;
import io.github.omgimanerd.bouncysquare.util.Util;

public class Game {

  private ViewPort viewPort_;
  private BackgroundManager backgroundManager_;
  private PlayerShape playerShape_;
  private PlatformManager platformManager_;

  public Game() {
    viewPort_ = new ViewPort();
    backgroundManager_ = new BackgroundManager();
    playerShape_ = new PlayerShape();
    platformManager_ = new PlatformManager();
  }

  public void update() {
    if (!isLost()) {
      viewPort_.update(playerShape_);
      backgroundManager_.update(viewPort_);
      playerShape_.update(viewPort_, platformManager_.getPlatforms());
      platformManager_.update(viewPort_, playerShape_.getScore());
    }
  }

  public void render(Canvas canvas) {
    backgroundManager_.render(canvas);
    playerShape_.render(canvas);
    platformManager_.render(canvas);
  }

  public boolean isLost() {
    return playerShape_.isLost();
  }

  public void onTouch(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      if (playerShape_.getMappedShape().contains(event.getX(), event.getY())
          && !playerShape_.isReleased()) {
        playerShape_.release();
        return;
      }

      if (event.getX() > Util.SCREEN_WIDTH / 2) {
        playerShape_.rotateClockwise();
      } else {
        playerShape_.rotateCounterClockwise();
      }
    }
  }

  public int getScore() {
    return playerShape_.getScore();
  }
}
