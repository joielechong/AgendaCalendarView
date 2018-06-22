package com.rilixtech.agendacalendarview.models;

import android.support.annotation.ColorInt;
import java.util.Calendar;
import java.util.Date;

public interface IDayItem {

  Date getDate();

  void setDate(Date date);

  int getValue();

  void setValue(int value);

  boolean isToday();

  void setToday(boolean today);

  boolean isSelected();

  void setSelected(boolean selected);

  boolean isFirstDayOfTheMonth();

  void setFirstDayOfTheMonth(boolean firstDayOfTheMonth);

  String getMonth();

  void setMonth(String month);

  int getDayOftheWeek();

  void setDayOftheWeek(int mDayOftheWeek);

  void setEventTotal(int total);

  int getEventTotal();

  void buildDayItemFromCal(Calendar calendar);

  String toString();

  boolean isWeekend();

  void setWeekend(boolean isWeekend);

  IDayItem copy();

  void setColor(@ColorInt int color);

  @ColorInt int getColor();
}
