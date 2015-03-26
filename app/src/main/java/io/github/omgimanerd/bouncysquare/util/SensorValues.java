package io.github.omgimanerd.bouncysquare.util;

/**
 * This is basically the noob-as-fuck way to store the accelerometer values
 * so that I don't have to pass them from MainActivity to GameView to Game to
 * Square.
 */
public class SensorValues {

  public static float SENSITIVITY;
  public static float[] ACCELEROMETER_VALUES = new float[3];

  public static float toSensitivityFromData(int dataValue) {
    return (dataValue + 1) * 0.5f;
  }

}
