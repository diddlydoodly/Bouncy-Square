package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.Iterator;
import java.util.LinkedList;

import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.CustomResources;
import io.github.omgimanerd.bouncysquare.util.Util;

public class PlatformManager {

  public static final int MAX_NUM_PLATFORMS = 4;
  public static final float PLATFORM_LENGTH = Util.SCREEN_WIDTH / 5;
  public static final float PLATFORM_HEIGHT = Util.SCREEN_WIDTH / 20;
  public static final float PLATFORM_VELOCITY = 5;

  private static final float MOVING_PLATFORM_RANGE = Util.SCREEN_HEIGHT / 9;
  private static final double PERCENT_SCALING_FACTOR = 25000;
  private static final double DEFAULT_MIN_PERCENT = 0.25;
  private static final double DEFAULT_MAX_PERCENT = 0.8;

  private LinkedList<Platform> platforms_;
  private float lastGeneratedHeight_;

  public PlatformManager() {
    platforms_ = new LinkedList<>();
    lastGeneratedHeight_ = 0;
  }

  public void update(ViewPort viewPort) {
    Iterator<Platform> iterator = platforms_.iterator();
    while (iterator.hasNext()) {
      Platform platform = iterator.next();
      platform.update(viewPort);
      if (viewPort.isOutOfBounds(platform.getPlatform())) {
        iterator.remove();
      }
    }
    while (platforms_.size() < MAX_NUM_PLATFORMS) {
      generateRandomPlatform(viewPort.getTop());
    }
  }

  public void render(Canvas canvas) {
    for (Platform platform : platforms_) {
      platform.render(canvas);
    }
  }

  public void generateDefault() {
    /**
     * A new platform is generated as soon as an old one is removed.
     * PlatformManager will automatically take care of spacing them evenly
     * apart. By manually declaring these four platforms,
     * we also ensure that there will only be four platforms in existence at
     * any given point.
     */
    generatePlatform(
        0,
        PlatformManager.PLATFORM_HEIGHT,
        Util.SCREEN_WIDTH,
        0,
        Color.BLACK);
    generatePlatform(
        0,
        Util.SCREEN_HEIGHT / 3 + PlatformManager.PLATFORM_HEIGHT,
        PlatformManager.PLATFORM_LENGTH,
        Util.SCREEN_HEIGHT / 3, CustomResources.selectRandomColor());
    generatePlatform(
        Util.SCREEN_WIDTH / 3,
        Util.SCREEN_HEIGHT * 2 / 3 + PlatformManager.PLATFORM_HEIGHT,
        Util.SCREEN_WIDTH / 3 + PlatformManager.PLATFORM_LENGTH,
        Util.SCREEN_HEIGHT * 2 / 3, CustomResources.selectRandomColor());
    generatePlatform(
        Util.SCREEN_WIDTH * 2 / 3,
        Util.SCREEN_HEIGHT + PlatformManager.PLATFORM_HEIGHT,
        Util.SCREEN_WIDTH * 2 / 3 + PlatformManager.PLATFORM_LENGTH,
        Util.SCREEN_HEIGHT, CustomResources.selectRandomColor());
  }

  public void generatePlatform(float left, float top,
                               float right, float bottom,
                               int color) {
    platforms_.add(new Platform(left, top, right, bottom, color));
    if (top > lastGeneratedHeight_) {
      lastGeneratedHeight_ = top;
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
  public static double getPercentMovingPlatformChance(double score,
                                                      double minPercent,
                                                      double maxPercent) {
    return (- PERCENT_SCALING_FACTOR /
        (score + (PERCENT_SCALING_FACTOR / (maxPercent - minPercent)))) +
        maxPercent;
  }

  public void generateRandomPlatform(double heightReached) {
    //TODO: improve platform generation
    float x = (int) (Math.random() * (Util.SCREEN_WIDTH - PLATFORM_LENGTH));
    float y = lastGeneratedHeight_ + Util.SCREEN_HEIGHT / 3;
    lastGeneratedHeight_ = y;
    Platform platform = new Platform(
        x,
        y,
        x + PLATFORM_LENGTH,
        y - PLATFORM_HEIGHT,
        CustomResources.selectRandomColor());
    if (Math.random() < getPercentMovingPlatformChance(heightReached,
                                                       DEFAULT_MIN_PERCENT,
                                                       DEFAULT_MAX_PERCENT)) {
      if (Math.random() < 0.33) {
        platform.setMotion((float) Math.random() * PLATFORM_VELOCITY,
                           0,
                           new float[] {0, Util.SCREEN_WIDTH},
                           new float[] {0, 0});
      } else if (Math.random() < 0.66) {
        platform.setMotion(0,
                           (float) Math.random() * PLATFORM_VELOCITY,
                           new float[] {0, 0},
                           new float[] {
                               y - MOVING_PLATFORM_RANGE,
                               y + MOVING_PLATFORM_RANGE});
      } else {
        platform.setMotion((float) Math.random() * PLATFORM_VELOCITY,
                           (float) Math.random() * PLATFORM_VELOCITY,
                           new float[] {0, Util.SCREEN_WIDTH},
                           new float[] {
                               y - MOVING_PLATFORM_RANGE,
                               y + MOVING_PLATFORM_RANGE});
      }
    }
    platforms_.add(platform);
  }

  public LinkedList<Platform> getPlatforms() {
    return platforms_;
  }
}
