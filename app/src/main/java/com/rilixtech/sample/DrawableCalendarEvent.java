package com.rilixtech.sample;

import com.rilixtech.agendacalendarview.models.AbstractBaseCalendarEvent;
import com.rilixtech.agendacalendarview.models.BaseCalendarEvent;
import com.rilixtech.agendacalendarview.models.CalendarEvent;

import com.rilixtech.agendacalendarview.models.IDayItem;
import com.rilixtech.agendacalendarview.models.IWeekItem;
import java.util.Calendar;

public class DrawableCalendarEvent extends BaseCalendarEvent {
  protected int mDrawableId;

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

  @Override public CalendarEvent copy() {
    return new DrawableCalendarEvent(this);
  }

  public static class Builder extends
      AbstractBaseCalendarEvent.Builder<DrawableCalendarEvent, DrawableCalendarEvent.Builder> {

    public Builder id(long id) {
      obj.mId = id;
      return thisObj;
    }

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

    public Builder isAllDay(boolean isAllDay) {
      obj.mIsAllDay = isAllDay;
      return thisObj;
    }

    public Builder isPlaceHolder(boolean isPlaceHolder) {
      obj.mIsPlaceHolder = isPlaceHolder;
      return thisObj;
    }

    public Builder isWeather(boolean isWeather) {
      obj.mIsWeather = isWeather;
      return thisObj;
    }

    public Builder duration(String duration) {
      obj.mDuration = duration;
      return thisObj;
    }

    public Builder dayReference(IDayItem dayReference) {
      obj.mDayReference = dayReference;
      return thisObj;
    }

    public Builder weekReference(IWeekItem weekReference) {
      obj.mWeekReference = weekReference;
      return thisObj;
    }

    public Builder weatherIcon(String weatherIcon) {
      obj.mWeatherIcon = weatherIcon;
      return thisObj;
    }

    public Builder temperature(double temperature) {
      obj.mTemperature = temperature;
      return thisObj;
    }

    public Builder allDay(boolean isAllDay) {
      obj.mIsAllDay = isAllDay;
      return thisObj;
    }

    public Builder drawableId(int drawableId) {
      obj.mDrawableId = drawableId;
      return thisObj;
    }

    public Builder calendarDayColor(int calendarDayColor) {
      obj.mCalendarDayColor = calendarDayColor;
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
