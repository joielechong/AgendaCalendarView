package com.rilixtech.agendacalendarview.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import com.rilixtech.agendacalendarview.R;

/**
 * Created by joielechong on 12/1/17.
 */
public class EventIndicatorView extends View {
  private Paint mPaint;
  private int mFirstCircleColor/* = Color.RED*/;
  private int desiredWidth;
  private int desiredHeight;

  private float mCircleRadius; // pixel
  private float mPlusWidth; // pixel
  private float mIndicatorMargin; // pixel
  private int mIndicatorAmount;
  private int mPlusColor;
  private int mSecondCircleColor;

  public EventIndicatorView(Context context) {
    super(context);
    init(null);
  }

  public EventIndicatorView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setStyle(Paint.Style.FILL);

    TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EventIndicatorView);

    // Default plus color is black
    mPlusColor = typedArray.getColor(R.styleable.EventIndicatorView_plusColor, Color.BLACK);
    // Default size is 2
    mCircleRadius = typedArray.getDimensionPixelSize(R.styleable.EventIndicatorView_circleRadius, 2);
    // Default margin size is 2
    mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.EventIndicatorView_indicatorMargin, 2);
    // Default plus size is 2
    mPlusWidth = typedArray.getDimensionPixelSize(R.styleable.EventIndicatorView_plusWidth, 2);
    // default indicator amount is 0
    mIndicatorAmount = typedArray.getInt(R.styleable.EventIndicatorView_indicatorAmount, 0);

    // default first color is Red
    mFirstCircleColor = typedArray.getColor(R.styleable.EventIndicatorView_firstCircleColor, Color.RED);
    // Default second circle color is 0xff6600ff
    mSecondCircleColor = typedArray.getColor(R.styleable.EventIndicatorView_secondCircleColor, 0xff6600ff);

    // Determine the size of minimum width by the circle size
    // This is for 3 view
    if (mIndicatorAmount <= 1) {
      desiredWidth = (int) (mCircleRadius * 2);
    } else if (mIndicatorAmount == 2) {
      desiredWidth = (int) ((mCircleRadius * 4) + mIndicatorMargin);
    } else {
      desiredWidth = (int) (mCircleRadius * 6 + (mIndicatorMargin * 2));
    }

    desiredHeight = (int) (mCircleRadius * 2);
    typedArray.recycle();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

    int width;
    if (widthMode == MeasureSpec.EXACTLY) {
      width = widthSize;
    } else if (widthMode == MeasureSpec.AT_MOST) {
      width = Math.min(desiredWidth, widthSize);
    } else {
      width = desiredWidth;
    }

    int height;
    if (heightMode == MeasureSpec.EXACTLY) {
      height = heightSize;
    } else if (heightMode == MeasureSpec.AT_MOST) {
      height = Math.min(desiredHeight, heightSize);
    } else {
      height = desiredHeight;
    }

    setMeasuredDimension(width, height);
  }

  @Override protected void onDraw(Canvas canvas) {
    int w = getWidth();
    int h = getHeight();

    int pl = getPaddingLeft();
    int pr = getPaddingRight();
    int pt = getPaddingTop();
    int pb = getPaddingBottom();

    int usableWidth = w - (pl + pr);
    int usableHeight = h - (pt + pb);
    int cx = pl + (usableWidth / 2);
    int cy = pt + (usableHeight / 2);

    if(mIndicatorAmount > 0) drawIndicator(canvas, cx, cy, mIndicatorAmount);
  }

  private void drawIndicator(Canvas canvas, int cx, int cy, int totalIndicator) {
    if (totalIndicator <= 1) {
      drawSingleIndicator(canvas, cx, cy);
    } else if (totalIndicator == 2) {
      drawDoubleIndicator(canvas, cx, cy);
    } else {
      drawMultipleIndicator(canvas, cx, cy);
    }
  }

  private void drawSingleIndicator(Canvas canvas, int cx, int cy) {
    mPaint.setColor(mFirstCircleColor);
    canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
  }

  private void drawDoubleIndicator(Canvas canvas, int cx, int cy) {
    mPaint.setColor(mFirstCircleColor);
    float x1 = cx - mCircleRadius - mIndicatorMargin / 2.0f;
    canvas.drawCircle(x1, cy, mCircleRadius, mPaint);

    mPaint.setColor(mSecondCircleColor);
    float x2 = cx + mIndicatorMargin / 2.0f + mCircleRadius;
    canvas.drawCircle(x2, cy, mCircleRadius, mPaint);
  }

  private void drawMultipleIndicator(Canvas canvas, int cx, int cy) {
    mPaint.setColor(mFirstCircleColor);
    canvas.drawCircle(cx - 2 * mCircleRadius - mIndicatorMargin, cy, mCircleRadius, mPaint);

    mPaint.setColor(mSecondCircleColor);
    canvas.drawCircle(cx, cy, mCircleRadius, mPaint);

    drawPlus(canvas, cx, cy);
  }

  private void drawPlus(Canvas canvas, int centerX, int centerY) {
    mPaint.setColor(mPlusColor);
    mPaint.setStrokeWidth(mPlusWidth);

    // draw " - "
    float x1 = centerX + mIndicatorMargin + mCircleRadius;
    float x2 = x1 + 2 * mCircleRadius;
    float centerLineX = x1 + mCircleRadius;
    canvas.drawLine(x1, centerY, x2, centerY, mPaint);

    // draw " | "
    canvas.drawLine(centerLineX, centerY - mCircleRadius, centerLineX, centerY + mCircleRadius, mPaint);
    mPaint.setStrokeWidth(0);
  }

  public float getCircleRadius() {
    return mCircleRadius;
  }

  /**
   * Set radius of view.
   *
   * @param radius radius in dp
   */
  public void setCircleRadius(float radius) {
    DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
    mCircleRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, dm);
    invalidate();
  }

  public float getIndicatorMargin() {
    return mIndicatorMargin;
  }

  /**
   * Set indicator margin for multiple indicator
   *
   * @param indicatorMargin margin in dp
   */
  public void setIndicatorMargin(float indicatorMargin) {
    DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
    mIndicatorMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorMargin, dm);
    invalidate();
  }

  public float getPlusWidth() {
    return mPlusWidth;
  }

  /**
   * Set plus width for plus indicator
   *
   * @param plusWidth in dp
   */
  public void setPlusWidth(float plusWidth) {
    DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
    mPlusWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, plusWidth, dm);
    invalidate();
  }

  public int getIndicatorAmount() {
    return mIndicatorAmount;
  }

  public void setIndicatorAmount(int indicatorAmount) {
    this.mIndicatorAmount = indicatorAmount;
    invalidate();
  }

  public int getFirstCircleColor() {
    return mFirstCircleColor;
  }

  public void setFirstCircleColor(int color) {
    this.mFirstCircleColor = color;
    invalidate();
  }

  public int getSecondCircleColor() {
    return mSecondCircleColor;
  }

  public void setSecondCircleColor(int color) {
    this.mSecondCircleColor = color;
    invalidate();
  }
}
