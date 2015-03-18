package io.github.omgimanerd.bouncysquare.game;

/**
 * Created by omgimanerd on 3/18/15.
 */
public class ViewPort {

  private float minY_;
  private float maxY_;

  public ViewPort() {

  }

  public float toCanvasCoords(float x) {
    return x - minY_;
  }
}
