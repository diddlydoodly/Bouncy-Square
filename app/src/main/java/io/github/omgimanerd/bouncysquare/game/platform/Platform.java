package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import io.github.omgimanerd.bouncysquare.game.Square;
import io.github.omgimanerd.bouncysquare.game.ViewPort;

public class Platform {

  /**
   * truePlatform_ stores the absolute position of the platform,
   * while mappedPlatform_ stores the canvas coordinates of the platform.
   * vx_, vy_, moveRangeX_, and moveRangeY_ are in absolute coordinates.
   */
  private RectF truePlatform_;
  private RectF mappedPlatform_;
  private boolean isMoving_;
  private float vx_;
  private float vy_;
  private float[] moveRangeX_;
  private float[] moveRangeY_;
  private boolean isSolid_;

  private Paint platformPaint_;

  public Platform(RectF rect, int color) {
    truePlatform_ = rect;
    mappedPlatform_ = new RectF();
    isMoving_ = false;
    vx_ = 0;
    vy_ = 0;
    moveRangeX_ = new float[2];
    moveRangeY_ = new float[2];
    isSolid_ = true;
    platformPaint_ = new Paint();
    platformPaint_.setColor(color);

  }

  public Platform(float left, float top, float right, float bottom, int color) {
    truePlatform_ = new RectF(left, top, right, bottom);
    mappedPlatform_ = new RectF();
    isMoving_ = false;
    vx_ = 0;
    vy_ = 0;
    moveRangeX_ = new float[2];
    moveRangeY_ = new float[2];
    isSolid_ = true;
    platformPaint_ = new Paint();
    platformPaint_.setColor(color);
  }

  public void update(ViewPort viewPort) {
    if (isMoving_) {
      truePlatform_.offset(vx_, vy_);
      if (truePlatform_.left < moveRangeX_[0]) {
        vx_ = Math.abs(vx_);
      } else if (truePlatform_.right > moveRangeX_[1]) {
        vx_ = -Math.abs(vx_);
      }
      if (truePlatform_.bottom < moveRangeY_[0]) {
        vy_ = Math.abs(vy_);
      } else if (truePlatform_.top > moveRangeY_[1]) {
        vy_ = -Math.abs(vy_);
      }
    }

    mappedPlatform_ = viewPort.mapToCanvas(truePlatform_);
  }

  public void render(Canvas canvas) {
    canvas.drawRect(mappedPlatform_, platformPaint_);
  }

  public boolean matchColor(Square square) {
    Log.d("color", square.getBottomColor() + " " + platformPaint_.getColor());
    return platformPaint_.getColor() == Color.BLACK ||
        square.getBottomColor() == platformPaint_.getColor();
  }

  public void setPlatform(RectF rect) {
    truePlatform_.set(rect);
  }

  public void setPlatform(float left, float top, float right, float bottom) {
    truePlatform_.set(left, top, right, bottom);
  }

  public RectF getPlatform() {
    return truePlatform_;
  }

  public float centerX() {
    return truePlatform_.centerX();
  }

  public float centerY() {
    return truePlatform_.centerY();
  }

  public boolean isSolid() {
    return isSolid_;
  }

  public Platform setMotion(float vx, float vy,
                            float[] moveRangeX, float[] moveRangeY) {
    isMoving_ = true;
    vx_ = vx;
    vy_ = vy;
    moveRangeX_ = moveRangeX;
    moveRangeY_ = moveRangeY;
    return this;
  }

  public Platform setSolid(boolean isSolid) {
    isSolid_ = isSolid;
    return this;
  }

  public Platform setColor(int color) {
    platformPaint_.setColor(color);
    return this;
  }
}
