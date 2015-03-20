package io.github.omgimanerd.bouncysquare.util;

/**
 * Created by omgimanerd on 3/19/15.
 */
public class Util {

  public static float SCREEN_HEIGHT;
  public static float SCREEN_WIDTH;

  public static float normalizeAngle(float angle) {
    while (angle < 0) {
      angle += 360;
    }
    return angle % 360;
  }
}
