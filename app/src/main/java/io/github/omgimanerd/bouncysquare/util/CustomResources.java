package io.github.omgimanerd.bouncysquare.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import io.github.omgimanerd.bouncysquare.R;

public class CustomResources {

  /**
   * All values in this class are filled when GameActivity is started.
   */
  public static int[] STANDARD_COLORS = new int[4];

  private static Resources res_;
  private static Bitmap square_;
  private static Bitmap background_;

  public static void init(Context context) {
    res_ = context.getResources();
    STANDARD_COLORS[0] = res_.getColor(R.color.STANDARD_GREEN);
    STANDARD_COLORS[1] = res_.getColor(R.color.STANDARD_YELLOW);
    STANDARD_COLORS[2] = res_.getColor(R.color.STANDARD_RED);
    STANDARD_COLORS[3] = res_.getColor(R.color.STANDARD_BLUE);
    square_ = BitmapFactory.decodeResource(res_, R.drawable.square);
    background_ = BitmapFactory.decodeResource(res_, R.drawable.warehouse1);
  }

  public static Drawable getDrawable(int id) {
    return res_.getDrawable(id);
  }

  public static String getString(int id) {
    return res_.getString(id);
  }

  public static Bitmap getSquare() {
    return square_;
  }

  public static Bitmap getBackground() {
    return background_;
  }

  public static int selectRandomColor() {
    return STANDARD_COLORS[(int) (Math.random() * 4)];
  }
}
