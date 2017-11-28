package com.github.tibolte.agendacalendarview.event;

import com.github.tibolte.agendacalendarview.models.IDayItem;
import java.util.Calendar;
import java.util.Observable;

/**
 * Created by joielechong on 11/28/17.
 */

public class DayClickedEvent {

  public Calendar mCalendar;
  public IDayItem mDayItem;

  public DayClickedEvent(IDayItem dayItem) {
    this.mCalendar = Calendar.getInstance();
    this.mCalendar.setTime(dayItem.getDate());
    this.mDayItem = dayItem;
  }

  public Calendar getCalendar() {
    return mCalendar;
  }

  public IDayItem getDay() {
    return mDayItem;
  }
}
