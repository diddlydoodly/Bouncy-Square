package io.github.omgimanerd.bouncysquare.util;

import android.graphics.Bitmap;

public class Visuals {

  /**
   * This array is filled when the GameActivity is started by getting the app
   * resources.
   */
  public static int[] STANDARD_COLORS = new int[4];
  public static int THE_CHOSEN_GREY;

  public static Bitmap SQUARE;

  public static int selectRandomColor() {
    return STANDARD_COLORS[(int) (Math.random() * 4)];
  }

}
