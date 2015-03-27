package io.github.omgimanerd.bouncysquare.util;

import android.content.Context;
import android.graphics.RectF;

public class Util {

  public static float SCREEN_HEIGHT;
  public static float SCREEN_WIDTH;
  public static float[] ACCELEROMETER_VALUES;

  public static void init(Context context) {
    Util.SCREEN_WIDTH = context.getResources().getDisplayMetrics().
        widthPixels;
    Util.SCREEN_HEIGHT = context.getResources().getDisplayMetrics().
        heightPixels;
    ACCELEROMETER_VALUES = new float[3];
  }

  public static float normalizeAngle(float angle) {
    while (angle < 0) {
      angle += 360;
    }
    return angle % 360;
  }

  /**
   * This method is needed because the built-in intersects() method for
   * RectF's does not work with "normal" coordinates. This method uses AABB
   * collision detection to quickly detect if two RectF's with "normal"
   * coordinates where top > bottom have collided.
   * @param a A RectF to test
   * @param b The RectF to test against the first
   */
  public static boolean intersects(RectF a, RectF b) {
    return Math.abs(a.centerX() - b.centerX()) <
               a.width() / 2 + b.width() / 2 &&
           Math.abs(a.centerY() - b.centerY()) <
               -a.height() / 2 + -b.height() / 2;
  }
}
