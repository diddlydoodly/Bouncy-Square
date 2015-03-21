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
}
