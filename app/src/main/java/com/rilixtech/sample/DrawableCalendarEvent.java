package com.rilixtech.sample;

import com.rilixtech.agendacalendarview.models.AbstractBaseCalendarEvent;
import com.rilixtech.agendacalendarview.models.BaseCalendarEvent;
import com.rilixtech.agendacalendarview.models.CalendarEvent;

import java.util.Calendar;

public class DrawableCalendarEvent extends BaseCalendarEvent {
  protected int mDrawableId;

  public DrawableCalendarEvent(long id, int color, String title, String description,
      String location, long dateStart, long dateEnd, int allDay, String duration, int drawableId) {
    super(id, color, title, description, location, dateStart, dateEnd, allDay, duration);
    this.mDrawableId = drawableId;
  }

  public DrawableCalendarEvent(String title, String description, String location, int color,
      Calendar startTime, Calendar endTime, boolean allDay, int drawableId) {
    super(title, description, location, color, startTime, endTime, allDay);
    this.mDrawableId = drawableId;
  }

  public DrawableCalendarEvent(DrawableCalendarEvent calendarEvent) {
    super(calendarEvent);
    this.mDrawableId = calendarEvent.getDrawableId();
  }

  public DrawableCalendarEvent() {
  }

  public int getDrawableId() {
    return mDrawableId;
  }

  public DrawableCalendarEvent setDrawableId(int drawableId) {
    this.mDrawableId = drawableId;
    return this;
  }

  // endregion

  // region Class - BaseCalendarEvent

  @Override public CalendarEvent copy() {
    return new DrawableCalendarEvent(this);
  }

  public static class Builder extends
      AbstractBaseCalendarEvent.Builder<DrawableCalendarEvent, DrawableCalendarEvent.Builder> {

    public Builder title(String title) {
      obj.mTitle = title;
      return thisObj;
    }

    public Builder description(String description) {
      obj.mDescription = description;
      return thisObj;
    }

    public Builder location(String location) {
      obj.mLocation = location;
      return thisObj;
    }

    public Builder color(int color) {
      obj.mColor = color;
      return thisObj;
    }

    public Builder startTime(Calendar startTime) {
      obj.mStartTime = startTime;
      return thisObj;
    }

    public Builder endTime(Calendar endTime) {
      obj.mEndTime = endTime;
      return thisObj;
    }

    public Builder allDay(boolean isAllDay) {
      obj.mAllDay = isAllDay;
      return thisObj;
    }

    public Builder drawableId(int drawableId) {
      obj.mDrawableId = drawableId;
      return thisObj;
    }

    protected DrawableCalendarEvent createObj() {
      return new DrawableCalendarEvent();
    }

    protected Builder getThis() {
      return this;
    }
  }
}
