package com.rilixtech.agendacalendarview.models;

import android.support.annotation.ColorInt;
import java.util.Calendar;

/**
 * Event model class containing the information to be displayed on the agenda view.
 */
public class BaseCalendarEvent extends AbstractBaseCalendarEvent {

  /**
   * Id of the event.
   */
  protected long mId;
  /**
   * Color to be displayed in the agenda view.
   */
  protected int mColor;
  /**
   * Title of the event.
   */
  protected String mTitle;
  /**
   * Description of the event.
   */
  protected String mDescription;
  /**
   * Where the event takes place.
   */
  protected String mLocation;
  /**
   * Calendar instance helping sorting the events per section in the agenda view.
   */
  private Calendar mInstanceDay;
  /**
   * Start time of the event.
   */
  protected Calendar mStartTime;
  /**
   * End time of the event.
   */
  protected Calendar mEndTime;
  /**
   * Indicates if the event lasts all day.
   */
  protected boolean mIsAllDay;
  /**
   * Tells if this BaseCalendarEvent instance is used as a placeholder in the agenda view, if there's
   * no event for that day.
   */
  protected boolean mIsPlaceHolder;
  /**
   * Tells if this BaseCalendarEvent instance is used as a forecast information holder in the agenda
   * view.
   */
  protected boolean mIsWeather;
  /**
   * Duration of the event.
   */
  protected String mDuration;
  /**
   * References to a DayItem instance for that event, used to link interaction between the
   * calendar view and the agenda view.
   */
  protected IDayItem mDayReference;
  /**
   * References to a WeekItem instance for that event, used to link interaction between the
   * calendar view and the agenda view.
   */
  protected IWeekItem mWeekReference;
  /**
   * Weather icon string returned by the Dark Sky API.
   */
  protected String mWeatherIcon;
  /**
   * Temperature value returned by the Dark Sky API.
   */
  protected double mTemperature;

  /**
   * Color of day in calendar. Using integer RGB value
   */
  protected int mCalendarDayColor;
  

  public BaseCalendarEvent() {}

  public static class Builder extends AbstractBaseCalendarEvent.Builder<BaseCalendarEvent, BaseCalendarEvent.Builder> {

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
      return null;
    }

    public Builder calendarDayColor(int calendarDayColor) {
      obj.mCalendarDayColor = calendarDayColor;
      return thisObj;
    }

    protected BaseCalendarEvent createObj() { return new BaseCalendarEvent(); }
    protected Builder getThis() { return this; }
  }

  public BaseCalendarEvent(BaseCalendarEvent calendarEvent) {
    this.mId = calendarEvent.mId;
    this.mTitle = calendarEvent.mTitle;
    this.mColor = calendarEvent.mColor;
    this.mDescription = calendarEvent.mDescription;
    this.mLocation = calendarEvent.mLocation;
    this.mStartTime = calendarEvent.mStartTime;
    this.mEndTime = calendarEvent.mEndTime;
    this.mIsAllDay = calendarEvent.mIsAllDay;
    this.mIsPlaceHolder = calendarEvent.mIsPlaceHolder;
    this.mIsWeather = calendarEvent.mIsWeather;
    this.mDuration = calendarEvent.mDuration;
    this.mDayReference = calendarEvent.mDayReference;
    this.mWeekReference = calendarEvent.mWeekReference;
    this.mWeatherIcon = calendarEvent.mWeatherIcon;
    this.mTemperature = calendarEvent.mTemperature;
    this.mCalendarDayColor = calendarEvent.mCalendarDayColor;
  }

  public int getColor() {
    return mColor;
  }

  public void setColor(int color) {
    this.mColor = color;
  }

  public String getDescription() {
    return mDescription;
  }

  public boolean isAllDay() {
    return mIsAllDay;
  }

  public void setAllDay(boolean allDay) {
    this.mIsAllDay = allDay;
  }

  public void setDescription(String description) {
    this.mDescription = description;
  }

  public Calendar getInstanceDay() {
    return mInstanceDay;
  }

  public void setInstanceDay(Calendar mInstanceDay) {
    this.mInstanceDay = mInstanceDay;
    this.mInstanceDay.set(Calendar.HOUR, 0);
    this.mInstanceDay.set(Calendar.MINUTE, 0);
    this.mInstanceDay.set(Calendar.SECOND, 0);
    this.mInstanceDay.set(Calendar.MILLISECOND, 0);
    this.mInstanceDay.set(Calendar.AM_PM, 0);
  }

  public Calendar getEndTime() {
    return mEndTime;
  }

  public void setEndTime(Calendar endTime) {
    this.mEndTime = endTime;
  }

  public void setPlaceholder(boolean placeholder) {
    mIsPlaceHolder = placeholder;
  }

  public boolean isPlaceholder() {
    return mIsPlaceHolder;
  }

  public long getId() {
    return mId;
  }

  public void setId(long mId) {
    this.mId = mId;
  }

  public String getLocation() {
    return mLocation;
  }

  public void setLocation(String location) {
    this.mLocation = location;
  }

  public Calendar getStartTime() {
    return mStartTime;
  }

  public void setStartTime(Calendar startTime) {
    this.mStartTime = startTime;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String mTitle) {
    this.mTitle = mTitle;
  }

  public String getDuration() {
    return mDuration;
  }

  public void setDuration(String duration) {
    this.mDuration = duration;
  }

  public boolean isPlaceHolder() {
    return mIsPlaceHolder;
  }

  public void setPlaceHolder(boolean mPlaceHolder) {
    this.mIsPlaceHolder = mPlaceHolder;
  }

  public boolean isWeather() {
    return mIsWeather;
  }

  public void setWeather(boolean mWeather) {
    this.mIsWeather = mWeather;
  }

  public IDayItem getDayReference() {
    return mDayReference;
  }

  public void setDayReference(IDayItem mDayReference) {
    this.mDayReference = mDayReference;
  }

  public IWeekItem getWeekReference() {
    return mWeekReference;
  }

  public void setWeekReference(IWeekItem mWeekReference) {
    this.mWeekReference = mWeekReference;
  }

  public String getWeatherIcon() {
    return mWeatherIcon;
  }

  public void setWeatherIcon(String mWeatherIcon) {
    this.mWeatherIcon = mWeatherIcon;
  }

  public double getTemperature() {
    return mTemperature;
  }

  public void setTemperature(double temperature) {
    this.mTemperature = temperature;
  }

  @ColorInt
  public int getCalendarDayColor() {
    return mCalendarDayColor;
  }

  public void setCalendarDayColor(@ColorInt int calendarDayColor) {
    this.mCalendarDayColor = calendarDayColor;
  }

  @Override public CalendarEvent copy() {
    return new BaseCalendarEvent(this);
  }

  @Override public String toString() {
    return "BaseCalendarEvent{" + "title='" + mTitle + ", instanceDay= " + mInstanceDay.getTime() + "}";
  }
}
