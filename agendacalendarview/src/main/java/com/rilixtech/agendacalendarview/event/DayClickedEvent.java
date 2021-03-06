package com.rilixtech.agendacalendarview.event;

import com.rilixtech.agendacalendarview.models.IDayItem;
import java.util.Calendar;

/**
 * Created by joielechong on 11/28/17.
 */

public class DayClickedEvent {

  private Calendar mCalendar;
  private IDayItem mDayItem;

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
