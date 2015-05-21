package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;

import io.github.omgimanerd.bouncysquare.game.Square;
import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.Util;

public class FlippingPlatform extends Platform {

  public static final float ROTATION_SPEED = 15;

  private float orientationAngle_;
  private float targetOrientationAngle_;

  public FlippingPlatform(float left, float top, float right, float bottom,
                          String color) {
    super(left, top, right, bottom, color);
  }

  public void update(ViewPort viewport) {
    if (targetOrientationAngle_ != orientationAngle_) {
      orientationAngle_ = Util.normalizeAngle(orientationAngle_ +
                                              ROTATION_SPEED);
    }
    super.update(viewport);
  }

  public void render(Canvas canvas) {
    canvas.save();
    canvas.rotate(orientationAngle_,
                  mappedPlatform_.centerX(),
                  mappedPlatform_.centerY());
    canvas.restore();
  }

  public void flip() {
    targetOrientationAngle_ = (targetOrientationAngle_ + 180) % 360;
  }

  public boolean matchColor(Square square) {
    return false;
  }
}
