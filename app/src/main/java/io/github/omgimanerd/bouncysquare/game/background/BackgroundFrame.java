package io.github.omgimanerd.bouncysquare.game.background;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.CustomResources;

public class BackgroundFrame {

  private static final float NEBULA_CHANCE = 0.25f;

  private RectF trueFrame_;
  private RectF mappedFrame_;
  private int starSize_;
  private boolean hasNebulae_;

  public BackgroundFrame(float left, float top, float right, float bottom,
                         int starSize, boolean hasNebulae) {
    trueFrame_ = new RectF(left, top, right, bottom);
    starSize_ = starSize;
    hasNebulae_ = hasNebulae;
  }

  public static BackgroundFrame generateRandomFrame(float left, float top,
                                             float right, float bottom) {
    int starSize = (int) Math.floor(
        CustomResources.BG_STARS.length * Math.random());
    boolean hasNebulae = Math.random() < NEBULA_CHANCE;
    return new BackgroundFrame(left, top, right, bottom, starSize, hasNebulae);
  }

  public void update(ViewPort viewPort) {
    mappedFrame_ = viewPort.mapToCanvas(trueFrame_);
  }

  public void redraw(Canvas canvas) {
    for (Bitmap bitmap : CustomResources.BG_SET) {
      canvas.drawBitmap(bitmap, null, mappedFrame_, null);
    }
    canvas.drawBitmap(CustomResources.BG_STARS[starSize_], null,
                      mappedFrame_, null);
    if (hasNebulae_) {
      canvas.drawBitmap(CustomResources.BG_NEBULA, null, mappedFrame_, null);
    }
  }

  public RectF getTrueFrame() {
    return trueFrame_;
  }
}
