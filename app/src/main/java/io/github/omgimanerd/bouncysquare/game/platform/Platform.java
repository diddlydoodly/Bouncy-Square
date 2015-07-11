package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.PlayerShape;
import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.Util;

public abstract class Platform {

  public static final float PLATFORM_LENGTH = Util.SCREEN_WIDTH / 5f;
  public static final float PLATFORM_HEIGHT = Util.SCREEN_WIDTH / 20f;
  public static final float BASE_BOUNCE_VELOCITY = Util.SCREEN_HEIGHT / 28f;
  public static final float PLATFORM_VELOCITY = Util.SCREEN_HEIGHT / 1000f;

  protected RectF truePlatform_;
  protected RectF mappedPlatform_;
  protected boolean isMoving_;
  protected float vx_;
  protected float vy_;
  protected float[] moveRangeX_;
  protected float[] moveRangeY_;
  protected float bounceVelocity_;

  public Platform(float left, float top, float right, float bottom) {
    truePlatform_ = new RectF(left, top, right, bottom);
    mappedPlatform_ = new RectF();
    isMoving_ = false;
    vx_ = 0;
    vy_ = 0;
    moveRangeX_ = new float[2];
    moveRangeY_ = new float[2];
    bounceVelocity_ = BASE_BOUNCE_VELOCITY;
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
    throw new UnsupportedOperationException("Unimplemented method.");
  }

  public void flip() {
    throw new UnsupportedOperationException("Unimplemented method.");
  }

  public boolean matchColor(PlayerShape playerShape) {
    throw new UnsupportedOperationException("Unimplemented method.");
  }

  public RectF getPlatform() {
    return truePlatform_;
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

  public float getBounceVelocity() {
    return bounceVelocity_;
  }

  public Platform setBounceVelocity(float bounceVelocity) {
    bounceVelocity_ = bounceVelocity;
    return this;
  }

}
