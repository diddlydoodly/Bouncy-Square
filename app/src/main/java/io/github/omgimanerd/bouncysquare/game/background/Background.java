package io.github.omgimanerd.bouncysquare.game.background;

import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.CustomResources;
import io.github.omgimanerd.bouncysquare.util.Util;

public class Background {

  // Frames will follow "normal" coordinate system and will be mapped by the
  // ViewPort.
  private BackgroundFrame[] frames_;
  private int bottomFrameIndex_;

  public Background() {
    frames_ = new BackgroundFrame[2];
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
  }

  public void update(ViewPort viewPort) {
    RectF tmp = frames_[bottomFrameIndex_].getTrueFrame();
    if (viewPort.isOutOfBounds(tmp)) {
      frames_[bottomFrameIndex_] = new BackgroundFrame(
          0, tmp.top + Util.SCREEN_HEIGHT, Util.SCREEN_WIDTH, tmp.top,
          CustomResources.getBackground());
      bottomFrameIndex_ = (bottomFrameIndex_ + 1) % frames_.length;
    }
  }

  public void render(Canvas canvas) {
    for (int i = 0; i < frames_.length; ++i) {
      frames_[i].redraw(canvas);
    }
  }
}
