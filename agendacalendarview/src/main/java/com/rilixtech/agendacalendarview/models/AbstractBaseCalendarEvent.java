package com.rilixtech.agendacalendarview.models;

import android.support.annotation.ColorInt;
import java.util.Calendar;

/**
 * Event model class containing the information to be displayed on the agenda view.
 */
public abstract class AbstractBaseCalendarEvent implements CalendarEvent {

  protected abstract static class Builder<T extends AbstractBaseCalendarEvent, B extends Builder<T, B>> {
    protected T obj;
    protected B thisObj;

    public abstract Builder id(long id);
    public abstract Builder title(String title);
    public abstract Builder color(@ColorInt int color);
    public abstract Builder description(String description);
    public abstract Builder location(String location);
    public abstract Builder startTime(Calendar startTime);
    public abstract Builder endTime(Calendar endTime);
    public abstract Builder isAllDay(boolean isAllDay);
    public abstract Builder isPlaceHolder(boolean isPlaceHolder);
    public abstract Builder isWeather(boolean isWeather);
    public abstract Builder duration(String duration);
    public abstract Builder dayReference(IDayItem dayReference);
    public abstract Builder weekReference(IWeekItem weekReference);
    public abstract Builder weatherIcon(String weatherIcon);
    public abstract Builder temperature(double temperature);
    public abstract Builder calendarDayColor(@ColorInt int calendarDayColor);

    public Builder() {
      obj = createObj(); thisObj = getThis();
    }

    protected abstract T createObj();
    protected abstract B getThis();

    public T build() { return obj; }
  }


  /**
   * Id of the event.
   */
  public abstract long getId();
  public abstract void setId(long mId);

  /**
   * Color to be displayed in the agenda view.
   */
  public abstract int getColor();
  public abstract void setColor(int color);

  /**
   * Title of the event.
   */
  public abstract String getTitle();
  public abstract void setTitle(String mTitle);

  /**
   * Description of the event.
   */
  public abstract String getDescription();
  public abstract void setDescription(String description);

  /**
   * Where the event takes place.
   */
  public abstract String getLocation();
  public abstract void setLocation(String location);

  /**
   * Calendar instance helping sorting the events per section in the agenda view.
   */
  public abstract Calendar getInstanceDay();

  /**
   * Start time of the event.
   */
  public abstract Calendar getStartTime();
  public abstract void setStartTime(Calendar startTime);

  /**
   * End time of the event.
   */
  public abstract Calendar getEndTime();
  public abstract void setEndTime(Calendar endTime);

  /**
   * Indicates if the event lasts all day.
   */
  public abstract boolean isAllDay();
  public abstract void setAllDay(boolean allDay);

  /**
   * Tells if this BaseCalendarEvent instance is used as a placeholder in the agenda view, if there's
   * no event for that day.
   */
  public abstract void setPlaceholder(boolean placeholder);
  public abstract boolean isPlaceholder();

  /**
   * Tells if this BaseCalendarEvent instance is used as a forecast information holder in the agenda
   * view.
   */
  public abstract boolean isWeather();
  public abstract void setWeather(boolean mWeather);

  /**
   * Duration of the event.
   */
  public abstract String getDuration();
  public abstract void setDuration(String duration);

  /**
   * References to a DayItem instance for that event, used to link interaction between the
   * calendar view and the agenda view.
   */
  public abstract IDayItem getDayReference();
  public abstract void setDayReference(IDayItem mDayReference);

  /**
   * References to a WeekItem instance for that event, used to link interaction between the
   * calendar view and the agenda view.
   */
  public abstract IWeekItem getWeekReference();
  public abstract void setWeekReference(IWeekItem mWeekReference);

  /**
   * Weather icon string returned by the Dark Sky API.
   */
  public abstract String getWeatherIcon();
  public abstract void setWeatherIcon(String mWeatherIcon);

  /**
   * Temperature value returned by the Dark Sky API.
   */
  public abstract double getTemperature();
  public abstract void setTemperature(double temperature);

  /**
   * Calendar day background color for highlighting the day with event.
   */
  @ColorInt
  public abstract int getCalendarDayColor();
  public abstract void setCalendarDayColor(@ColorInt int calendarDayColor);

  public abstract CalendarEvent copy();

  @Override public abstract String toString();
}
