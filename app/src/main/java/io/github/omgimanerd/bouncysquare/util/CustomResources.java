package io.github.omgimanerd.bouncysquare.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

import io.github.omgimanerd.bouncysquare.R;

public class CustomResources {

  private static Resources res_;

  /**
   * All values in this class are filled when GameActivity is started.
   */
  public static int[] COLORS;
  public static Bitmap SQUARE;

  public static void init(Context context) {
    res_ = context.getResources();
    COLORS = new int[] {
        res_.getColor(R.color.RED),
        res_.getColor(R.color.BLUE),
        res_.getColor(R.color.GREEN),
        res_.getColor(R.color.YELLOW)
    };

    SQUARE = BitmapFactory.decodeResource(res_, R.drawable.square);
  }

  public static Drawable getDrawable(int id) {
    return res_.getDrawable(id);
  }

  public static String getString(int id) {
    return res_.getString(id);
  }

  public static int getRandomColor() {
    return COLORS[(int) Math.floor(Math.random() * COLORS.length)];
  }
}
