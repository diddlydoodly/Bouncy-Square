package io.github.omgimanerd.bouncysquare.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This is basically the noob-as-fuck way to store the accelerometer values
 * so that I don't have to pass them from MainActivity to GameView to Game to
 * Square. Also takes care of storing the highscore. Allows for data
 * persistence during and between sessions.
 */
public class PersistentData {

  private static final String PERSISTENT_DATA_KEY = "bouncy_square";

  private static final String SENSITIVITY_KEY = "sensitivity";
  private static final int DEFAULT_UNSET_SENSITIVITY = 2;

  private static final String HIGHSCORE_KEY = "highscore";
  private static final int DEFAULT_HIGHSCORE = 0;

  private static SharedPreferences persistentData_;
  private static SharedPreferences.Editor editor_;
  private static int sensitivity_;

  public static void init(Context context) {
    persistentData_ = context.getSharedPreferences(PERSISTENT_DATA_KEY, 0);
    sensitivity_ = getRawSensitivity();
  }

  public static int getRawSensitivity() {
    return persistentData_.getInt(SENSITIVITY_KEY, DEFAULT_UNSET_SENSITIVITY);
  }

  public static float getConvertedSensitivity() {
    return (sensitivity_ + 1) * 0.5f;
  }

  public static void setSensitivity(int sensitivity) {
    editor_ = persistentData_.edit();
    editor_.putInt(SENSITIVITY_KEY, sensitivity);
    editor_.apply();

    sensitivity_ = sensitivity;
  }

  public static int getHighScore() {
    return persistentData_.getInt(HIGHSCORE_KEY, DEFAULT_HIGHSCORE);
  }

  public static void setHighScore(int highScore) {
    editor_ = persistentData_.edit();
    editor_.putInt(HIGHSCORE_KEY, highScore);
    editor_.apply();
  }
}
