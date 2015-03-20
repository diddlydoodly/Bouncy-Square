package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;

import io.github.omgimanerd.bouncysquare.game.ViewPort;

/**
 * Created by omgimanerd on 3/19/15.
 */
public abstract class Platform {

  private float x_;
  private float y_;
  private float length_;

  public abstract void update(ViewPort viewPort);

  public abstract void render(Canvas canvas);
}
