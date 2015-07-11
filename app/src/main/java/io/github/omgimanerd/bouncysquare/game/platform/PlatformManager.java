package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;
import android.util.Log;

import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.CustomResources;
import io.github.omgimanerd.bouncysquare.util.Util;

public class PlatformManager {

  /**
   * The number of platforms out on the screen is a constant number.
   */
  private static final int NUM_PLATFORMS = 4;
  private static final int FLIPPING_PLATFORM_THRESHOLD = 40;
  private static final float FLIPPING_PLATFORM_CHANCE = 0.33f;
  private static final float VERTICAL_RANGE = Util.SCREEN_HEIGHT / 15f;
  private static final float FIRST_PLATFORM_BOOST = 2.75f;

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
    platforms_[0] = new NormalPlatform(
        0.20f * Util.SCREEN_WIDTH,
        0.33f * Util.SCREEN_HEIGHT + Platform.PLATFORM_HEIGHT,
        0.80f * Util.SCREEN_WIDTH,
        0.33f * Util.SCREEN_HEIGHT,
        CustomResources.getRandomPlatformColor()).setBounceVelocity(
            Platform.BASE_BOUNCE_VELOCITY * FIRST_PLATFORM_BOOST);
    platforms_[1] = new NormalPlatform(
        -10,
        0.66f * Util.SCREEN_HEIGHT + NormalPlatform.PLATFORM_HEIGHT,
        -10,
        0.66f * Util.SCREEN_HEIGHT,
        CustomResources.getRandomPlatformColor());
    platforms_[2] = new NormalPlatform(
        -10,
        Util.SCREEN_HEIGHT + NormalPlatform.PLATFORM_HEIGHT,
        -10,
        Util.SCREEN_HEIGHT,
        CustomResources.getRandomPlatformColor());
    platforms_[3] = new NormalPlatform(
        -10,
        1.33f * Util.SCREEN_HEIGHT + NormalPlatform.PLATFORM_HEIGHT,
        -10,
        1.33f * Util.SCREEN_HEIGHT,
        CustomResources.getRandomPlatformColor());
    bottomPlatformIndex_ = 0;
    topPlatformIndex_ = NUM_PLATFORMS - 1;
  }

  public void update(ViewPort viewPort, int score) {
    for (int i = 0; i < NUM_PLATFORMS; ++i) {
      if (i == bottomPlatformIndex_ &&
          viewPort.isOutOfBounds(platforms_[i].getPlatform())) {
        platforms_[i] = generateNextRandomPlatform(score);
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
   * Given the player's score, returns the chance that further generated
   * platforms will move.
   * @param score The player's score.
   * @return The percentage that the generated platform will move.
   */
  private double getPercentMovingPlatformChance(double score) {
    return Math.max(Math.min(Math.log10(0.01 * score) + 1, 1), 0);
  }

  private Platform generateNextRandomPlatform(double score) {
    float x = Util.randRangeInt((int) (Util.SCREEN_WIDTH - NormalPlatform
        .PLATFORM_LENGTH));
    float y = platforms_[topPlatformIndex_].getPlatform().top + Util
        .SCREEN_HEIGHT / 3;

    double progressPercent = getPercentMovingPlatformChance(score);
    Platform platform;

    if (score > FLIPPING_PLATFORM_THRESHOLD &&
        Math.random() < FLIPPING_PLATFORM_CHANCE) {
      platform = new FlippingPlatform(
          x,
          y,
          x + Platform.PLATFORM_LENGTH,
          y - Platform.PLATFORM_HEIGHT,
          CustomResources.getRandomFlippingPlatformColors());
    } else {
      platform = new NormalPlatform(
          x,
          y,
          x + Platform.PLATFORM_LENGTH,
          y - Platform.PLATFORM_HEIGHT,
          CustomResources.getRandomPlatformColor());
    }

    if (Math.random() < progressPercent) {
      if (Math.random() < 0.33) {
        platform.setMotion((float) progressPercent * Platform.PLATFORM_VELOCITY,
                           0,
                           new float[] {0, Util.SCREEN_WIDTH},
                           new float[] {0, 0});
      } else if (Math.random() < 0.66) {
        platform.setMotion(0,
                           (float) progressPercent * Platform.PLATFORM_VELOCITY,
                           new float[] {0, 0},
                           new float[] {
                               y - VERTICAL_RANGE,
                               y + VERTICAL_RANGE});
      } else {
        platform.setMotion((float) progressPercent * Platform.PLATFORM_VELOCITY,
                           (float) progressPercent * Platform.PLATFORM_VELOCITY,
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
