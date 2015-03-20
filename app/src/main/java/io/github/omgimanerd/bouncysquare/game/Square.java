package io.github.omgimanerd.bouncysquare.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

import io.github.omgimanerd.bouncysquare.game.platform.Platform;
import io.github.omgimanerd.bouncysquare.util.Util;

/**
 * Created by omgimanerd on 3/18/15.
 */
public class Square {

  // TODO: find a good acceleration factor
  private static final int ACCELERATION_Y = 0;
  private static final int[] SIDE_COLORS = new int[] {
      Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
  private static final int CORNER_DOT_COLOR = Color.GRAY;
  private static final int SIDE_LENGTH = (int) (Util.SCREEN_WIDTH / 8);
  private static final int STROKE_WIDTH = 10;

  /**
   * trueSquare_ stores the absolute position of the square. We rely on the
   * ViewPort to convert the trueSquare coordinates to canvas coordinates for
   * drawing.
   */
  private RectF trueSquare_;
  private RectF mappedSquare_;
  private float vx_;
  private float vy_;
  private int orientation_;

  private Paint[] drawnSquareSidePaints_;
  private Paint cornerDotPaint_;

  public Square() {
    trueSquare_ = new RectF(Util.SCREEN_WIDTH / 2 - SIDE_LENGTH / 2,
                            Util.SCREEN_HEIGHT / 4 - SIDE_LENGTH / 2,
                            Util.SCREEN_WIDTH / 2 + SIDE_LENGTH / 2,
                            Util.SCREEN_HEIGHT / 4 + SIDE_LENGTH / 2);
    vx_ = 0;
    vy_ = 0;
    orientation_ = 0;
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
    trueSquare_.offset(vx_, vy_);
    mappedSquare_ = viewport.mapToCanvas(trueSquare_);
  }

  public void render(Canvas canvas) {
    canvas.drawLine(mappedSquare_.right, mappedSquare_.bottom,
                    mappedSquare_.left, mappedSquare_.bottom,
                    drawnSquareSidePaints_[orientation_]);
    canvas.drawLine(mappedSquare_.left, mappedSquare_.bottom,
                    mappedSquare_.left, mappedSquare_.top,
                    drawnSquareSidePaints_[(orientation_ + 1) % 4]);
    canvas.drawLine(mappedSquare_.left, mappedSquare_.top,
                    mappedSquare_.right, mappedSquare_.top,
                    drawnSquareSidePaints_[(orientation_ + 2) % 4]);
    canvas.drawLine(mappedSquare_.right, mappedSquare_.top,
                    mappedSquare_.right, mappedSquare_.bottom,
                    drawnSquareSidePaints_[(orientation_ + 3) % 4]);
    canvas.drawCircle(mappedSquare_.right, mappedSquare_.bottom,
                      STROKE_WIDTH / 2, cornerDotPaint_);
    canvas.drawCircle(mappedSquare_.left, mappedSquare_.bottom,
                      STROKE_WIDTH / 2, cornerDotPaint_);
    canvas.drawCircle(mappedSquare_.right, mappedSquare_.top,
                      STROKE_WIDTH / 2, cornerDotPaint_);
    canvas.drawCircle(mappedSquare_.left, mappedSquare_.top,
                      STROKE_WIDTH / 2, cornerDotPaint_);
  }

  public RectF getSquare() {
    return trueSquare_;
  }

  public int getOrientation() {
    return orientation_;
  }

  public void rotateClockwise() {
    // TODO: animate this shit.
    orientation_ = (orientation_ == 3) ? 0 : orientation_ + 1;
  }

  public void rotateCounterClockwise() {
    orientation_ = (orientation_ == 0) ? 3 : orientation_ - 1;
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
