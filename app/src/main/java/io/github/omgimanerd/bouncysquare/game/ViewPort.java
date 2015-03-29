package io.github.omgimanerd.bouncysquare.game;

import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.util.Util;

/**
 * ViewPort's coordinates are absolute coordinates and not canvas coordinates.
 * ViewPort will map given coordinates to the canvas.
 */
public class ViewPort {

  private RectF viewPort_;
  private float upperScrollBound_;

  public ViewPort() {
    viewPort_ = new RectF(0, Util.SCREEN_HEIGHT, Util.SCREEN_WIDTH, 0);
    upperScrollBound_ = Util.SCREEN_HEIGHT * 3 / 4;
  }

  public void update(Square square) {
    if (square.getSquare().top > upperScrollBound_) {
      upperScrollBound_ = square.getSquare().top;
      viewPort_.offsetTo(0, upperScrollBound_ + Util.SCREEN_HEIGHT / 4);
    }
  }

  public float getTop() {
    return viewPort_.top;
  }

  /**
   * Maps the given RectF to it's position relative to the canvas so that it
   * can be drawn.
   * @param rect The RectF to map
   * @return The appropriately mapped RectF
   */
  public RectF mapToCanvas(RectF rect) {
    RectF mappedRectF = new RectF(rect);
    mappedRectF.offsetTo(rect.left, Util.SCREEN_HEIGHT - (rect.bottom -
        viewPort_.bottom));
    float tmp = mappedRectF.top;
    mappedRectF.top = mappedRectF.bottom;
    mappedRectF.bottom = tmp;
    return mappedRectF;
  }

  /**
   * Returns if the given RectF is visible or will be visible at some
   * point inside of the current viewport
   * bounds.
   * @param rect The RectF to test for visibility
   * @return true if the RectF given will show up later at some point,
   * false if it will never show up again and should be removed.
   */
  public boolean isOutOfBounds(RectF rect) {
    return !(rect.top >= viewPort_.bottom);
  }
}
