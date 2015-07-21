package io.github.omgimanerd.bouncysquare.util;

import android.content.Context;
import android.graphics.RectF;

import java.security.InvalidParameterException;

public class Util {

  public static float SCREEN_HEIGHT;
  public static float SCREEN_WIDTH;

  public static float ACCELEROMETER_TILT;

  public static void init(Context context) {
    Util.SCREEN_WIDTH = context.getResources().getDisplayMetrics().
        widthPixels;
    Util.SCREEN_HEIGHT = context.getResources().getDisplayMetrics().
        heightPixels;
  }

  /**
   * Takes an angle and returns a coterminal angle from 0 <= angle < 360.
   * @param angle The angle to normalize
   * @return the normalized angle.
   */
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
   * coordinates (top > bottom) have collided.
   * @param a A RectF to test
   * @param b The RectF to test against the first
   */
  public static boolean intersects(RectF a, RectF b) {
    return Math.abs(a.centerX() - b.centerX()) <
               a.width() / 2 + b.width() / 2 &&
           Math.abs(a.centerY() - b.centerY()) <
               -a.height() / 2 + -b.height() / 2;
  }

  /**
   * Generates a random integer within the given min, inclusive and max,
   * exclusive.
   * @param min The minimum bound, inclusive.
   * @param max The maximum bound, exclusive.
   * @return An integer within the bounds of min and max.
   */
  public static int randRangeInt(int min, int max) {
    if (min > max) {
      throw new InvalidParameterException("Ya done fucked up");
    }
    return (int) (Math.floor(Math.random() * (max - min))) + min;
  }

  public static int randRangeInt(int max) {
    return randRangeInt(0, max);
  }

  public static int getSign(float n) {
    return n == 0 ? (int) n : (int) (n / Math.abs(n));
  }
}
