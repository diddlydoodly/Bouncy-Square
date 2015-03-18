package io.github.omgimanerd.bouncysquare.game;

/**
 * Created by omgimanerd on 3/18/15.
 */
public class Square {

  /**
   * The positional variables are absolutes and are converted to canvas
   * coordinates by the ViewPort.
   */
  private float x_;
  private float y_;
  private int sideLength_;

  public Square() {

  }

  public float getX() {
    return x_;
  }

  public void setX(float x) {
    x_ = x;
  }

  public float getY() {
    return y_;
  }

  public void setY(float y) {
    y_ = y;
  }
}
