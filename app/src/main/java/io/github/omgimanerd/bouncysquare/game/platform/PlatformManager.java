package io.github.omgimanerd.bouncysquare.game.platform;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import io.github.omgimanerd.bouncysquare.R;
import io.github.omgimanerd.bouncysquare.game.Game;
import io.github.omgimanerd.bouncysquare.game.Square;
import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.Util;

public class PlatformManager {

  public static final float PLATFORM_LENGTH = Util.SCREEN_WIDTH / 5;
  public static final float PLATFORM_HEIGHT = Util.SCREEN_WIDTH / 20;
  public static final float PLATFORM_VELOCITY = 5;

  private static final float MOVING_PLATFORM_CHANCE = 0.25f;
  private static final float MOVING_PLATFORM_RANGE = Util.SCREEN_HEIGHT / 9;

  private ArrayList<Platform> platforms_;

  public PlatformManager() {
    platforms_ = new ArrayList<>();
  }

  public void update(ViewPort viewPort) {
    for (Platform platform : platforms_) {
      platform.update(viewPort);
    }
    int i = 0;
    while (i < platforms_.size()) {
      if (!viewPort.isVisible(platforms_.get(i).getPlatform())) {
        platforms_.remove(i);
        generateRandomPlatform(viewPort);
      } else {
        i++;
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

  }

  public void generateRandomPlatform(ViewPort viewPort) {
    //TODO: improve platform generation
    float x = (int) (Math.random() * (Util.SCREEN_WIDTH - PLATFORM_LENGTH));
    float y = viewPort.getTop();
    Random rand_ = new Random();
    int color = Game.STANDARD_COLORS[
        rand_.nextInt(Game.STANDARD_COLORS.length)];
    Platform platform = new Platform(
        x,
        y,
        x + PLATFORM_LENGTH,
        y - PLATFORM_HEIGHT,
        color);
    if (Math.random() < MOVING_PLATFORM_CHANCE) {
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

  public ArrayList<Platform> getPlatforms() {
    return platforms_;
  }
}
