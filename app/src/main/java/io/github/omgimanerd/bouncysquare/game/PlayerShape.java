package io.github.omgimanerd.bouncysquare.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.platform.Platform;
import io.github.omgimanerd.bouncysquare.util.CustomResources;
import io.github.omgimanerd.bouncysquare.util.Util;

public class PlayerShape {
  private static final float ACCELERATION_Y = -Util.SCREEN_HEIGHT / 800f;
  // ROTATION_SPEED is in degrees/update.
  private static final float ROTATION_SPEED = 15;
  private static final float SIDE_LENGTH = Util.SCREEN_WIDTH / 6;
  private static final RectF STARTING_RECT = new RectF(
      Util.SCREEN_WIDTH / 2 - SIDE_LENGTH / 2,
      Util.SCREEN_HEIGHT / 2 + SIDE_LENGTH / 2,
      Util.SCREEN_WIDTH / 2 + SIDE_LENGTH / 2,
      Util.SCREEN_HEIGHT / 2 - SIDE_LENGTH / 2
  );

  /**
   * truePosition_ stores the absolute position of the square. We rely on the
   * ViewPort to convert the truePosition_'s coordinates to canvas coordinates for
   * drawing.
   * Every time update() is called, the canvas coordinates of truePosition_ are
   * stored in mappedPosition_.
   */
  private RectF truePosition_;
  private RectF mappedPosition_;
  private int sides_;
  private float vx_;
  private float vy_;

  /**
   * When the square is rotating, targetOrientationAngle_ keeps track of what
   * the final angle should be while orientationAngle_ rotates towards it.
   * Both angles are in degrees.
   */
  private float orientationAngle_;
  private float targetOrientationAngle_;

  private boolean isSolid_;
  private boolean isLost_;
  private boolean isReleased_;

  private int score_;

  public PlayerShape() {
    truePosition_ = new RectF(STARTING_RECT);
    mappedPosition_ = new RectF();
    vx_ = 0;
    vy_ = 0;

    orientationAngle_ = 0;
    targetOrientationAngle_ = 0;

    isSolid_ = true;
    isLost_ = false;
    isReleased_ = false;

    score_ = 0;
  }

  public void update(ViewPort viewport, Platform[] platforms) {

    if (isReleased_) {
      // Updates the square's horizontal velocity according to the tilt of the
      // phone.
      vx_ = - Util.SCALED_ACCELEROMETER_VALUES[0];
      // Updates the vertical velocity by constantly accelerating downward.
      vy_ += ACCELERATION_Y;

      // Prevents the square from clipping into the left or right side of the
      // screen.
      if (truePosition_.left + vx_ <= 0) {
        truePosition_.offsetTo(0, truePosition_.top);
      } else if (truePosition_.right + vx_ >= Util.SCREEN_WIDTH) {
        truePosition_.offsetTo(Util.SCREEN_WIDTH - truePosition_.width(),
                               truePosition_.top);
      } else {
        truePosition_.offset(vx_, 0);
      }

      // Handles upward velocity when the square bounces off of a platform.
      // The square should bounce if it is falling downward and is above a
      // platform.
      if (isSolid_) {
        for (Platform platform : platforms) {
          if (Util.intersects(truePosition_, platform.getPlatform()) &&
              vy_ < 0 &&
              truePosition_.bottom > platform.getPlatform().bottom) {
            isSolid_ = platform.matchColor(this);

            if (isSolid_) {
              vy_ = platform.getBounceVelocity();
              score_++;
            } else {
              vy_ = Platform.BASE_BOUNCE_VELOCITY / 5;
            }
          }
        }
      }
    }

    truePosition_.offset(0, vy_);
    isLost_ = viewport.isOutOfBounds(truePosition_);

    // Incremements or decrements the orientation angle until it matches the
    // target orientation angle.
    if (targetOrientationAngle_ != orientationAngle_ &&
        Util.normalizeAngle(
            targetOrientationAngle_ - orientationAngle_) <= 180) {
      orientationAngle_ = Util.normalizeAngle(
          orientationAngle_ + ROTATION_SPEED);
    } else if (targetOrientationAngle_ != orientationAngle_ &&
        Util.normalizeAngle(
            targetOrientationAngle_ - orientationAngle_) > 180) {
      orientationAngle_ = Util.normalizeAngle(
          orientationAngle_ - ROTATION_SPEED);
    }

    mappedPosition_ = viewport.mapToCanvas(truePosition_);
  }

  public void render(Canvas canvas) {
    canvas.save();
    canvas.rotate(orientationAngle_,
                  mappedPosition_.centerX(), mappedPosition_.centerY());
    canvas.drawBitmap(CustomResources.SQUARE, null, mappedPosition_, null);
    canvas.restore();
  }

  public RectF getShape() {
    return truePosition_;
  }

  public RectF getMappedShape() {
    return mappedPosition_;
  }

  public int getBottomColor() {
    return CustomResources.COLORS[(Math.round((orientationAngle_) / 90)
        % 4)];
  }

  public void rotateClockwise() {
    targetOrientationAngle_ = Util.normalizeAngle(targetOrientationAngle_ + 90);
  }

  public void rotateCounterClockwise() {
    targetOrientationAngle_ = Util.normalizeAngle(targetOrientationAngle_ - 90);
  }

  public boolean isLost() {
    return isLost_;
  }

  public boolean isReleased() {
    return isReleased_;
  }

  public void release() {
    isReleased_ = true;
  }

  public int getScore() {
    return score_;
  }
}
