package com.rilixtech.agendacalendarview.models;

import android.support.annotation.ColorInt;
import com.rilixtech.agendacalendarview.CalendarManager;
import com.rilixtech.agendacalendarview.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * Day model class.
 */
public class DayItem implements IDayItem {
  private Date mDate;
  private int mValue;
  private int mDayOfTheWeek;
  private boolean mToday;
  private boolean mFirstDayOfTheMonth;
  private boolean mSelected;
  private String mMonth;
  private int mEventTotal;
  private boolean isWeekend;
  private int color;

  // only for cleanDay
  public DayItem() {

  }

  public DayItem(DayItem original) {
    this.mDate = original.getDate();
    this.mValue = original.getValue();
    this.mToday = original.isToday();
    this.mDayOfTheWeek = original.getDayOftheWeek();
    this.mFirstDayOfTheMonth = original.isFirstDayOfTheMonth();
    this.mSelected = original.isSelected();
    this.mMonth = original.getMonth();
    this.color = original.getColor();
  }

  public Date getDate() {
    return mDate;
  }

  public void setDate(Date date) {
    this.mDate = date;
  }

  public int getValue() {
    return mValue;
  }

  public void setValue(int value) {
    this.mValue = value;
  }

  public boolean isToday() {
    return mToday;
  }

  public void setToday(boolean today) {
    this.mToday = today;
  }

  public boolean isSelected() {
    return mSelected;
  }

  public void setSelected(boolean selected) {
    this.mSelected = selected;
  }

  public boolean isFirstDayOfTheMonth() {
    return mFirstDayOfTheMonth;
  }

  public void setFirstDayOfTheMonth(boolean firstDayOfTheMonth) {
    this.mFirstDayOfTheMonth = firstDayOfTheMonth;
  }

  public String getMonth() {
    return mMonth;
  }

  public void setMonth(String month) {
    this.mMonth = month;
  }

  public int getDayOftheWeek() {
    return mDayOfTheWeek;
  }

  public void setDayOftheWeek(int mDayOftheWeek) {
    this.mDayOfTheWeek = mDayOftheWeek;
  }

  public void setEventTotal(int eventTotal) {
    mEventTotal = eventTotal;
  }

  public int getEventTotal() {
    return mEventTotal;
  }

  public void buildDayItemFromCal(Calendar calendar) {
    Date date = calendar.getTime();
    this.mDate = date;

    this.mValue = calendar.get(Calendar.DAY_OF_MONTH);
    this.mToday = DateHelper.sameDate(calendar, CalendarManager.getInstance().getToday());
    this.mMonth = CalendarManager.getInstance().getMonthHalfNameFormat().format(date);
    if (this.mValue == 1) {
      this.mFirstDayOfTheMonth = true;
    }
  }

  @Override @ColorInt public int getColor() {
    return color;
  }

  @Override public void setColor(@ColorInt int color) {
    this.color = color;
  }

  @Override public String toString() {
    return "DayItem{" + "Date='" + mDate.toString() + ", value=" + mValue + '}';
  }

  @Override public boolean isWeekend() {
    return isWeekend;
  }

  @Override public void setWeekend(boolean isWeekend) {
    this.isWeekend = isWeekend;
  }

  @Override public IDayItem copy() {
    return new DayItem(this);
  }
}
