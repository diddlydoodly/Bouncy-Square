package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.PlayerShape;
import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.Util;

public class NormalPlatform extends Platform {

  /**
   * truePlatform_ stores the absolute position of the platform,
   * while mappedPlatform_ stores the canvas coordinates of the platform.
   * vx_, vy_, moveRangeX_, and moveRangeY_ are in absolute coordinates.
   */
  protected int color_;

  protected Paint platformPaint_;

  public NormalPlatform(float left, float top, float right, float bottom,
                        int color) {
    super(left, top, right, bottom);
    color_ = color;

    platformPaint_ = new Paint();
    platformPaint_.setColor(color_);
  }

  public void update(ViewPort viewPort) {
    super.update(viewPort);
  }

  public void render(Canvas canvas) {
    canvas.drawRect(mappedPlatform_, platformPaint_);
  }

  public boolean matchColor(PlayerShape playerShape) {
    return playerShape.getBottomColor() == color_;
  }
}
