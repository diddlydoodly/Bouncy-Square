package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BreakingPlatform extends Platform {

  protected int color_;

  protected Paint platformPaint_;

  public BreakingPlatform(int left, int top, int right, int bottom, int color) {
    super(left, top, right, bottom);
    color_ = color;

    platformPaint_ = new Paint();
    platformPaint_.setColor(color_);
  }

  public void update() {}

  public void render(Canvas canvas) {

  }

}
