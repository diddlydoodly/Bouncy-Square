package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.PlayerShape;
import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.Util;

public class FlippingPlatform extends Platform {

  public static final int NUM_COLORS = 2;
  public static final float ROTATION_SPEED = 15;

  private float orientationAngle_;
  private float targetOrientationAngle_;
  private int[] colors_;

  private Paint[] paints_;

  public FlippingPlatform(float left, float top, float right, float bottom,
                          int[] colors) {
    super(left, top, right, bottom, 0);

    colors_ = colors;
    if (colors.length != 2) {
      throw new Error("You fucked up.");
    }
    paints_ = new Paint[NUM_COLORS];
    for (int i = 0; i < NUM_COLORS; ++i) {
      paints_[i] = new Paint();
      paints_[i].setColor(colors_[i]);
    }
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
    canvas.drawRect(mappedPlatform_.left, mappedPlatform_.top,
                    mappedPlatform_.right, mappedPlatform_.centerY(),
                    paints_[0]);
    canvas.drawLine(mappedPlatform_.left, mappedPlatform_.centerY(),
                    mappedPlatform_.right, mappedPlatform_.bottom,
                    paints_[1]);
    canvas.restore();
  }

  public void flip() {
    targetOrientationAngle_ = (targetOrientationAngle_ + 180) % 360;
  }

  public boolean matchColor(PlayerShape playerShape) {
    return playerShape.getBottomColor() == colors_[(int)
        targetOrientationAngle_ / 180];
  }
}
