package io.github.omgimanerd.bouncysquare.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import io.github.omgimanerd.bouncysquare.game.platform.Platform;
import io.github.omgimanerd.bouncysquare.util.SensorValues;
import io.github.omgimanerd.bouncysquare.util.Util;

public class Square {
  // TODO: find a good acceleration factor
  private static final float ACCELERATION_Y = -0.75f;
  private static final float ROTATION_SPEED = 9;
  private static final int CORNER_DOT_COLOR = Color.GRAY;
  private static final int SIDE_LENGTH = (int) (Util.SCREEN_WIDTH / 8);
  private static final int STROKE_WIDTH = 10;
  public static final int[] SIDE_COLORS = new int[] {
      Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
  private static final RectF STARTING_RECT = new RectF(
      Util.SCREEN_WIDTH / 2 - SIDE_LENGTH / 2,
      Util.SCREEN_HEIGHT / 2 + SIDE_LENGTH / 2,
      Util.SCREEN_WIDTH / 2 + SIDE_LENGTH / 2,
      Util.SCREEN_HEIGHT / 2 - SIDE_LENGTH / 2);

  /**
   * trueSquare_ stores the absolute position of the square. We rely on the
   * ViewPort to convert the trueSquare_'s coordinates to canvas coordinates for
   * drawing.
   * Every time update() is called, the canvas coordinates of trueSquare_ are
   * stored in mappedSquare_.
   */
  private RectF trueSquare_;
  private RectF mappedSquare_;
  private float vx_;
  private float vy_;

  /**
   * When the square is rotating, targetOrientationAngle_ keeps track of what
   * the final angle should be while orientationAngle_ rotates towards it.
   * Both angles are in degrees.
   */
  private float orientationAngle_;
  private float targetOrientationAngle_;

  private Paint[] drawnSquareSidePaints_;
  private Paint cornerDotPaint_;

  public Square() {
    trueSquare_ = new RectF(STARTING_RECT);
    mappedSquare_ = new RectF();
    vx_ = 0;
    vy_ = 0;

    orientationAngle_ = 0;
    targetOrientationAngle_ = 0;

    drawnSquareSidePaints_ = new Paint[4];
    for (int i = 0; i < 4; ++i) {
      drawnSquareSidePaints_[i] = new Paint();
      drawnSquareSidePaints_[i].setColor(SIDE_COLORS[i]);
      drawnSquareSidePaints_[i].setStrokeWidth(STROKE_WIDTH);
    }
    cornerDotPaint_ = new Paint();
    cornerDotPaint_.setColor(CORNER_DOT_COLOR);
  }

  public void update(ViewPort viewport, ArrayList<Platform> platforms) {
    vx_ = -SensorValues.ACCELEROMETER_VALUES[0];
    vy_ += ACCELERATION_Y;

    if (trueSquare_.left + vx_ <= 0) {
      trueSquare_.offsetTo(0, trueSquare_.top);
    } else if (trueSquare_.right + vx_ >= Util.SCREEN_WIDTH) {
      trueSquare_.offsetTo(Util.SCREEN_WIDTH - trueSquare_.width(),
                           trueSquare_.top);
    } else {
      trueSquare_.offset(vx_, 0);
    }

    for (Platform platform : platforms) {
      if (vy_ < 0 && Util.intersects(trueSquare_, platform.getPlatform())) {
        vy_ = 25;
      }
    }

    // Bottom case, never actually happens.
    if (trueSquare_.bottom + vy_ <= 0) {
      // Since RectF expects bottom > top, trueSquare_.height() returns
      // negative.
      trueSquare_.offsetTo(trueSquare_.left, -trueSquare_.height());
      vy_ = 0;
    } else {
      trueSquare_.offset(0, vy_);
    }

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

    mappedSquare_ = viewport.mapToCanvas(trueSquare_);
    // This is necessary to since the drawing using lines goes out of the
    // bounding box.
    mappedSquare_.inset(STROKE_WIDTH, -STROKE_WIDTH);
  }

  public void render(Canvas canvas) {
    canvas.save();
    canvas.rotate(orientationAngle_,
                  mappedSquare_.centerX(), mappedSquare_.centerY());
    canvas.drawLine(mappedSquare_.right, mappedSquare_.bottom,
                    mappedSquare_.left, mappedSquare_.bottom,
                    drawnSquareSidePaints_[0]);
    canvas.drawLine(mappedSquare_.left, mappedSquare_.bottom,
                    mappedSquare_.left, mappedSquare_.top,
                    drawnSquareSidePaints_[1]);
    canvas.drawLine(mappedSquare_.left, mappedSquare_.top,
                    mappedSquare_.right, mappedSquare_.top,
                    drawnSquareSidePaints_[2]);
    canvas.drawLine(mappedSquare_.right, mappedSquare_.top,
                    mappedSquare_.right, mappedSquare_.bottom,
                    drawnSquareSidePaints_[3]);
    canvas.drawCircle(mappedSquare_.right, mappedSquare_.bottom,
                      STROKE_WIDTH / 2, cornerDotPaint_);
    canvas.drawCircle(mappedSquare_.left, mappedSquare_.bottom,
                      STROKE_WIDTH / 2, cornerDotPaint_);
    canvas.drawCircle(mappedSquare_.right, mappedSquare_.top,
                      STROKE_WIDTH / 2, cornerDotPaint_);
    canvas.drawCircle(mappedSquare_.left, mappedSquare_.top,
                      STROKE_WIDTH / 2, cornerDotPaint_);
    canvas.restore();
  }

  public RectF getSquare() {
    return trueSquare_;
  }

  public float getOrientationAngle() {
    return orientationAngle_;
  }

  public int getBottomColor() {
    return (int) (orientationAngle_ / 90);
  }

  public void rotateClockwise() {
    targetOrientationAngle_ = Util.normalizeAngle(targetOrientationAngle_ + 90);
  }

  public void rotateCounterClockwise() {
    targetOrientationAngle_ = Util.normalizeAngle(targetOrientationAngle_ - 90);
  }

  public float getVx() {
    return vx_;
  }

  public void setVx(float vx) {
    vx_ = vx;
  }

  public float getVy() {
    return vy_;
  }

  public void setVy(float vy) {
    vy_ = vy;
  }
}
