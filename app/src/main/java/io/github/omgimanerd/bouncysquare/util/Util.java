package io.github.omgimanerd.bouncysquare.util;

import android.graphics.RectF;
import android.util.Log;

public class Util {

  public static float SCREEN_HEIGHT;
  public static float SCREEN_WIDTH;

  public static float normalizeAngle(float angle) {
    while (angle < 0) {
      angle += 360;
    }
    return angle % 360;
  }

  public static void outputRect(RectF rect) {
    Log.d("test", rect.left + " " + rect.top + " " + rect.right + " " + rect
        .bottom);
  }

  /**
   * This method is needed because the built-in intersects() method for
   * RectF's does not work with "normal" coordinates. This method uses AABB
   * collision detection to quickly detect if two RectF's with "normal"
   * coordinates where top > bottom have collided.
   * @param a
   * @param b
   */
  public static boolean intersects(RectF a, RectF b) {
    return Math.abs(a.centerX() - b.centerX()) <
               a.width() / 2 + b.width() / 2 &&
           Math.abs(a.centerY() - b.centerY()) <
               -a.height() / 2 + -b.height() / 2;
  }
}
