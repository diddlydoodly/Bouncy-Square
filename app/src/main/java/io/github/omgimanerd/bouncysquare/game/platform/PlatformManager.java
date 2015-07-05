package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;

import io.github.omgimanerd.bouncysquare.R;
import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.CustomResources;
import io.github.omgimanerd.bouncysquare.util.Util;

public class PlatformManager {

  /**
   * The number of platforms out on the screen is a constant number.
   */
  private static final int NUM_PLATFORMS = 4;
  private static final float PLATFORM_VELOCITY = 5;
  private static final float VERTICAL_RANGE = Util.SCREEN_HEIGHT / 15f;

  private static final double PERCENT_SCALING_FACTOR = 20000;
  private static final double DEFAULT_MIN_PERCENT = 0.25;
  private static final double DEFAULT_MAX_PERCENT = 0.95;

  private Platform[] platforms_;
  private int bottomPlatformIndex_;
  private int topPlatformIndex_;

  public PlatformManager() {
    platforms_ = new Platform[NUM_PLATFORMS];
    /**
     * A new platform is generated as soon as an old one is removed.
     * PlatformManager will automatically take care of spacing them evenly
     * apart.
     */
    platforms_[0] = new Platform(
        0.20f * Util.SCREEN_WIDTH,
        0.33f * Util.SCREEN_HEIGHT + Platform.PLATFORM_HEIGHT,
        0.80f * Util.SCREEN_WIDTH,
        0.33f * Util.SCREEN_HEIGHT,
        CustomResources.getRandomColor())
        .setBounceVelocity(Platform.BASE_BOUNCE_VELOCITY * 2.5f);
    platforms_[1] = new Platform(
        -10,
        0.66f * Util.SCREEN_HEIGHT + Platform.PLATFORM_HEIGHT,
        -10,
        0.66f * Util.SCREEN_HEIGHT,
        CustomResources.getRandomColor());
    platforms_[2] = new Platform(
        -10,
        Util.SCREEN_HEIGHT + Platform.PLATFORM_HEIGHT,
        -10,
        Util.SCREEN_HEIGHT,
        CustomResources.getRandomColor());
    platforms_[3] = new Platform(
        -10,
        1.33f * Util.SCREEN_HEIGHT + Platform.PLATFORM_HEIGHT,
        -10,
        1.33f * Util.SCREEN_HEIGHT,
        CustomResources.getRandomColor());
    bottomPlatformIndex_ = 0;
    topPlatformIndex_ = NUM_PLATFORMS - 1;
  }

  public void update(ViewPort viewPort, int heightScore) {
    for (int i = 0; i < NUM_PLATFORMS; ++i) {
      if (i == bottomPlatformIndex_ &&
          viewPort.isOutOfBounds(platforms_[i].getPlatform())) {
        platforms_[i] = generateNextRandomPlatform(heightScore);
        bottomPlatformIndex_ = (bottomPlatformIndex_ + 1) % NUM_PLATFORMS;
        topPlatformIndex_ = (topPlatformIndex_ + 1) % NUM_PLATFORMS;
      }
      platforms_[i].update(viewPort);
    }
  }

  public void render(Canvas canvas) {
    for (int i = 0; i < NUM_PLATFORMS; ++i) {
      platforms_[i].render(canvas);
    }
  }

  /**
   * Given the height of the viewport, which determines how far the player
   * has progressed, this method returns the percent chance in (minPercent,
   * maxPercent) that the next platform generated will be a moving platform.
   * Recommend passing in a min-max range of (0.25, 0.8), which are preset in
   * PlatformManager defaults.
   * Preconditions; (0 < minPercent < 1) && (minPercent < maxPercent)
   * @param score The score or height that the player has reached
   * @param minPercent The lower bound of all generated percentages.
   * @param maxPercent The upper bound of all generated percentages.
   * @return The percentage that a platform will generate.
   */
  private double getPercentMovingPlatformChance(double score,
                                                double minPercent,
                                                double maxPercent) {
    return (- PERCENT_SCALING_FACTOR /
        (score + (PERCENT_SCALING_FACTOR / (maxPercent - minPercent)))) +
        maxPercent;
  }

  private Platform generateNextRandomPlatform(double heightReached) {
    float x = (int) (Math.random() * (Util.SCREEN_WIDTH -
        Platform.PLATFORM_LENGTH));
    float y = platforms_[topPlatformIndex_].getPlatform().top + Util
        .SCREEN_HEIGHT / 3;
    Platform platform = new Platform(
        x,
        y,
        x + Platform.PLATFORM_LENGTH,
        y - Platform.PLATFORM_HEIGHT,
        CustomResources.getRandomColor());

    double progressPercent = getPercentMovingPlatformChance(
        heightReached, DEFAULT_MIN_PERCENT, DEFAULT_MAX_PERCENT);
    if (Math.random() < progressPercent) {
      if (Math.random() < 0.33) {
        platform.setMotion((float) progressPercent * PLATFORM_VELOCITY,
                           0,
                           new float[] {0, Util.SCREEN_WIDTH},
                           new float[] {0, 0});
      } else if (Math.random() < 0.66) {
        platform.setMotion(0,
                           (float) progressPercent * PLATFORM_VELOCITY,
                           new float[] {0, 0},
                           new float[] {
                               y - VERTICAL_RANGE,
                               y + VERTICAL_RANGE});
      } else {
        platform.setMotion((float) progressPercent * PLATFORM_VELOCITY,
                           (float) progressPercent * PLATFORM_VELOCITY,
                           new float[] {0, Util.SCREEN_WIDTH},
                           new float[] {
                               y - VERTICAL_RANGE,
                               y + VERTICAL_RANGE});
      }
    }
    return platform;
  }

  public Platform[] getPlatforms() {
    return platforms_;
  }
}
