package io.github.omgimanerd.bouncysquare.util;

import android.graphics.Color;

public class Colors {

  /**
   * This array is filled when the GameActivity is started by getting the app
   * resources.
   */
  public static int[] STANDARD_COLORS = new int[4];

  public static int THE_CHOSEN_GREY;

  public static int selectRandomColor() {
    return STANDARD_COLORS[(int) (Math.random() * 4)];
  }

}
