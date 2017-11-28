package com.github.tibolte.agendacalendarview.utils;

import com.github.tibolte.agendacalendarview.models.IDayItem;
import java.util.Calendar;
import java.util.Observable;

/**
 * Created by joielechong on 11/28/17.
 */

public class DayClickedEvent extends Observable {

  public Calendar mCalendar;
  public IDayItem mDayItem;

  private static DayClickedEvent INSTANCE = null;

  private static DayClickedEvent getInstance() {
    if(INSTANCE == null) {
      INSTANCE = new DayClickedEvent();
    }
    return INSTANCE;
  }

  public DayClickedEvent() {

  }

  public DayClickedEvent(IDayItem dayItem) {
    this.mCalendar = Calendar.getInstance();
    this.mCalendar.setTime(dayItem.getDate());
    this.mDayItem = dayItem;

    setChanged();
    notifyObservers();

  }

  public Calendar getCalendar() {
    return mCalendar;
  }

  public IDayItem getDay() {
    return mDayItem;
  }
}
