package io.github.omgimanerd.bouncysquare.util;

/**
 * This is basically the noob-as-fuck way to store the accelerometer values
 * so that I don't have to pass them from MainActivity to GameView to Game to
 * Square.
 */
public class SensorValues {

  public static float SENSITIVITY = 1.5f;
  public static float[] ACCELEROMETER_VALUES = new float[3];

}
