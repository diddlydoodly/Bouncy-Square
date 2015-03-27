package io.github.omgimanerd.bouncysquare.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This is basically the noob-as-fuck way to store the accelerometer values
 * so that I don't have to pass them from MainActivity to GameView to Game to
 * Square.
 */
public class PersistentData {

  private static final String PERSISTENT_DATA_KEY = "bouncy_square";

  private static final String SENSITIVITY_KEY = "sensitivity";
  private static final int DEFAULT_SENSITIVITY = 2;

  private static final String HIGHSCORE_KEY = "highscore";
  private static final int DEFAULT_HIGHSCORE = 0;

  private static SharedPreferences persistentData_;
  private static SharedPreferences.Editor editor_;

  public static void init(Context context) {
    persistentData_ = context.getSharedPreferences(PERSISTENT_DATA_KEY, 0);
    editor_ = persistentData_.edit();
  }

  public static float getSensitivity() {
    return ((persistentData_.getInt(SENSITIVITY_KEY, DEFAULT_SENSITIVITY) + 1)
        * 0.5f);
  };

  public static void setSensitivity(int sensitivity) {
    editor_.putInt(SENSITIVITY_KEY, sensitivity);
    editor_.commit();
  }

  public static int getHighScore() {
    return persistentData_.getInt(HIGHSCORE_KEY, DEFAULT_HIGHSCORE);
  }

  public static void setHighScore(int highScore) {
    editor_.putInt(HIGHSCORE_KEY, highScore);
    editor_.commit();
  }
}
