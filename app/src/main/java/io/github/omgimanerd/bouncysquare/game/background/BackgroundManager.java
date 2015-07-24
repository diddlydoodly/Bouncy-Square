package io.github.omgimanerd.bouncysquare.game.background;

import android.graphics.Canvas;

import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.Util;

public class BackgroundManager {

  private static final int NUM_FRAMES = 3;

  // Frames will follow "normal" coordinate system and will be mapped by the
  // ViewPort.
  private BackgroundFrame[] frames_;
  private int bottomFrameIndex_;
  private int topFrameIndex_;

  public BackgroundManager() {
    frames_ = new BackgroundFrame[NUM_FRAMES];
    frames_[0] = BackgroundFrame.generateRandomFrame(
        0, Util.SCREEN_HEIGHT, Util.SCREEN_WIDTH, 0);
    frames_[1] = BackgroundFrame.generateRandomFrame(
        0, 2 * Util.SCREEN_HEIGHT, Util.SCREEN_WIDTH, Util.SCREEN_HEIGHT);
    frames_[2] = BackgroundFrame.generateRandomFrame(
        0, 3 * Util.SCREEN_HEIGHT, Util.SCREEN_WIDTH, 2 * Util.SCREEN_HEIGHT);
    bottomFrameIndex_ = 0;
    topFrameIndex_ = NUM_FRAMES - 1;
  }

  public void update(ViewPort viewPort) {
    for (int i = 0; i < frames_.length; ++i) {
      if (i == bottomFrameIndex_ &&
          viewPort.isOutOfBounds(frames_[bottomFrameIndex_].getTrueFrame())) {
        frames_[bottomFrameIndex_] = getNextFrame();
        bottomFrameIndex_ = (bottomFrameIndex_ + 1) % NUM_FRAMES;
        topFrameIndex_ = (topFrameIndex_ + 1) % NUM_FRAMES;
      }
      frames_[i].update(viewPort);
    }
  }

  public void render(Canvas canvas) {
    for (BackgroundFrame frame : frames_) {
      frame.redraw(canvas);
    }
  }

  private BackgroundFrame getNextFrame() {
    return BackgroundFrame.generateRandomFrame(
        0,
        frames_[topFrameIndex_].getTrueFrame().top + Util.SCREEN_HEIGHT,
        Util.SCREEN_WIDTH,
        frames_[topFrameIndex_].getTrueFrame().top
    );
  }
}
