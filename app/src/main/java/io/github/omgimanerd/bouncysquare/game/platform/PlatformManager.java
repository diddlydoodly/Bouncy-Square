package io.github.omgimanerd.bouncysquare.game.platform;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import io.github.omgimanerd.bouncysquare.game.Square;
import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.Util;

public class PlatformManager {

  private static final int[] PLATFORM_COLORS = Square.SIDE_COLORS;
  private static final float PLATFORM_LENGTH = Util.SCREEN_WIDTH / 5;
  private static final float PLATFORM_HEIGHT = Util.SCREEN_WIDTH / 20;

  private ArrayList<Platform> platforms_;

  public PlatformManager() {
    platforms_ = new ArrayList<>();
  }

  public void update(ViewPort viewPort) {
    for (Platform platform : platforms_) {
      platform.update(viewPort);
    }
//    int i = 0;
//    while (i < platforms_.size()) {
//      if (!viewPort.isVisible(platforms_.get(i).getPlatform())) {
//        platforms_.remove(i);
//      } else {
//        i++;
//      }
//    }
//    if (platforms_.size() < 3) {
//      generateRandomPlatform(viewPort);
//    }
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
    Random rand_ = new Random();
    float x = rand_.nextInt((int) (Util.SCREEN_WIDTH - PLATFORM_LENGTH));
    float y = viewPort.getTop() + rand_.nextInt((int) (Util.SCREEN_HEIGHT / 4));
    int color = PLATFORM_COLORS[rand_.nextInt(PLATFORM_COLORS.length)];
    platforms_.add(new Platform(x, y, x - PLATFORM_LENGTH, y - PLATFORM_HEIGHT,
                                color));
  }

  public ArrayList<Platform> getPlatforms() {
    return platforms_;
  }
}
