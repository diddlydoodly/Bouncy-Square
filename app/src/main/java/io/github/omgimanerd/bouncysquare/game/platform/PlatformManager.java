package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.CustomResources;
import io.github.omgimanerd.bouncysquare.util.Util;

public class PlatformManager {

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
  }

  public void render(Canvas canvas) {
    for (Platform platform : platforms_) {
      platform.render(canvas);
    }
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
   * has progressed, this method returns the percent chance in (0.25, 0.9)
   * that the next platform generated will be a moving platform.
   * Follows the formula.
   * @param score
   * @param minPercent
   * @param maxPercent
   * @return
   */
  public static double getPercentMovingPlatformChance(double score,
                                                      double minPercent,
                                                      double maxPercent) {
    return (- PERCENT_SCALING_FACTOR /
        (score + (PERCENT_SCALING_FACTOR / (maxPercent - minPercent)))) +
        maxPercent;
  }

  public void generateRandomPlatform(double score) {
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
    if (Math.random() < getPercentMovingPlatformChance(score,
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
