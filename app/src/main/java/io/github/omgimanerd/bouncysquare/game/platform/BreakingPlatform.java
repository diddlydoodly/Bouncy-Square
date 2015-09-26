package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;
import android.graphics.Paint;

import io.github.omgimanerd.bouncysquare.game.PlayerShape;
import io.github.omgimanerd.bouncysquare.game.ViewPort;

public class BreakingPlatform extends Platform {

  private static float STARTING_OPACITY = 75.0f;
  private static float OPACITY_DECREASE_RATE = 1.5f;

  protected int color_;
  protected float opacity_;

  protected Paint platformPaint_;

  public BreakingPlatform(float left, float top, float right, float bottom, int color) {
    super(left, top, right, bottom);
    color_ = color;
    opacity_ = STARTING_OPACITY;

    platformPaint_ = new Paint();
    platformPaint_.setColor(color_);
    platformPaint_.setAlpha((int) opacity_);
  }

  public void update(ViewPort viewport) {
    super.update(viewport);

    if (hasBeenBouncedOn()) {
      opacity_ = Math.max(opacity_ - OPACITY_DECREASE_RATE, 0);
    }
    platformPaint_.setAlpha((int) opacity_);
  }

  public void render(Canvas canvas) {
    canvas.drawRect(mappedPlatform_, platformPaint_);
  }

  public boolean matchColor(PlayerShape playerShape) {
    return playerShape.getBottomColor() == color_ && !hasBeenBouncedOn();
  }
}
