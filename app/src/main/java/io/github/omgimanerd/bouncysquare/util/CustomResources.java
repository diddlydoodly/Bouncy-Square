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
   * All values in this class are filled when MenuActivity is started.
   */
  public static String[] STANDARD_COLORS;
  public static Bitmap SQUARE;
  public static HashMap<String, Bitmap[]> PLATFORMS;
  public static Bitmap[] BG_SET;
  public static Bitmap[] BG_STARS;
  public static Bitmap BG_NEBULA;

  public static void init(Context context) {
    res_ = context.getResources();
    STANDARD_COLORS = new String[] {
        getString(R.string.red),
        getString(R.string.blue),
        getString(R.string.green),
        getString(R.string.yellow)
    };
    SQUARE = BitmapFactory.decodeResource(res_, R.drawable.square);
    PLATFORMS = new HashMap<>();
    PLATFORMS.put(getString(R.string.black), new Bitmap[] {
        BitmapFactory.decodeResource(res_, R.drawable.yellow1),
        BitmapFactory.decodeResource(res_, R.drawable.yellow2),
        BitmapFactory.decodeResource(res_, R.drawable.yellow3),
        BitmapFactory.decodeResource(res_, R.drawable.yellow4)
    });
    PLATFORMS.put(getString(R.string.red), new Bitmap[] {
        BitmapFactory.decodeResource(res_, R.drawable.red1),
        BitmapFactory.decodeResource(res_, R.drawable.red2),
        BitmapFactory.decodeResource(res_, R.drawable.red3),
        BitmapFactory.decodeResource(res_, R.drawable.red4)
    });
    PLATFORMS.put(getString(R.string.blue), new Bitmap[] {
        BitmapFactory.decodeResource(res_, R.drawable.blue1),
        BitmapFactory.decodeResource(res_, R.drawable.blue2),
        BitmapFactory.decodeResource(res_, R.drawable.blue3),
        BitmapFactory.decodeResource(res_, R.drawable.blue4)
    });
    PLATFORMS.put(getString(R.string.green), new Bitmap[] {
        BitmapFactory.decodeResource(res_, R.drawable.green1),
        BitmapFactory.decodeResource(res_, R.drawable.green2),
        BitmapFactory.decodeResource(res_, R.drawable.green3),
        BitmapFactory.decodeResource(res_, R.drawable.green4)
    });
    PLATFORMS.put(getString(R.string.yellow), new Bitmap[] {
        BitmapFactory.decodeResource(res_, R.drawable.yellow1),
        BitmapFactory.decodeResource(res_, R.drawable.yellow2),
        BitmapFactory.decodeResource(res_, R.drawable.yellow3),
        BitmapFactory.decodeResource(res_, R.drawable.yellow4)
    });

    BG_SET = new Bitmap[] {
        BitmapFactory.decodeResource(res_, R.drawable.bg_black),
        BitmapFactory.decodeResource(res_, R.drawable.bg_green_lines)
    };
    BG_STARS = new Bitmap[] {
        BitmapFactory.decodeResource(res_, R.drawable.bg_small_stars),
        BitmapFactory.decodeResource(res_, R.drawable.bg_medium_stars),
        BitmapFactory.decodeResource(res_, R.drawable.bg_large_stars)
    };
    BG_NEBULA = BitmapFactory.decodeResource(res_, R.drawable.bg_nebula);
  }

  public static Drawable getDrawable(int id) {
    return res_.getDrawable(id);
  }

  public static String getString(int id) {
    return res_.getString(id);
  }

  public static String selectRandomColor() {
    return STANDARD_COLORS[(int) (Math.random() * 4)];
  }
}
