package io.github.omgimanerd.bouncysquare.game.background;

import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.CustomResources;
import io.github.omgimanerd.bouncysquare.util.Util;

public class Background {

  private static final int NUM_FRAMES = 3;

  // Frames will follow "normal" coordinate system and will be mapped by the
  // ViewPort.
  private BackgroundFrame[] frames_;
  private int bottomFrameIndex_;
  private int topFrameIndex_;

  public Background() {
    frames_ = new BackgroundFrame[NUM_FRAMES];
    frames_[0] = new BackgroundFrame(0, Util.SCREEN_HEIGHT,
                                     Util.SCREEN_WIDTH, 0,
                                     CustomResources.getBackground());
    frames_[1] = new BackgroundFrame(0, 2 * Util.SCREEN_HEIGHT,
                                     Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT,
                                     CustomResources.getBackground());
    frames_[2] = new BackgroundFrame(0, 3 * Util.SCREEN_HEIGHT,
                                     Util.SCREEN_WIDTH, 2 * Util.SCREEN_HEIGHT,
                                     CustomResources.getBackground());
    bottomFrameIndex_ = 0;
    topFrameIndex_ = NUM_FRAMES - 1;
  }

  public void update(ViewPort viewPort) {
    for (int i = 0; i < frames_.length; ++i) {
      if (i == bottomFrameIndex_) {
        if (viewPort.isOutOfBounds(frames_[bottomFrameIndex_].getTrueFrame())) {
          frames_[bottomFrameIndex_] = new BackgroundFrame(
              0,
              frames_[topFrameIndex_].getTrueFrame().top + Util.SCREEN_HEIGHT,
              Util.SCREEN_WIDTH,
              frames_[topFrameIndex_].getTrueFrame().top,
              CustomResources.getBackground());
          bottomFrameIndex_ = (bottomFrameIndex_ + 1) % NUM_FRAMES;
          topFrameIndex_ = (topFrameIndex_ + 1) % NUM_FRAMES;
        }
      }
      frames_[i].update(viewPort);
    }
  }

  public void render(Canvas canvas) {
    for (int i = 0; i < frames_.length; ++i) {
      frames_[i].redraw(canvas);
    }
  }
}
