package com.rilixtech.agendacalendarview.models;

import java.util.Calendar;

/**
 * Event model class containing the information to be displayed on the agenda view.
 */
public abstract class AbstractBaseCalendarEvent implements CalendarEvent {

  /**
   * Id of the event.
   */
  private long mId;
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
  protected boolean mAllDay;
  /**
   * Tells if this BaseCalendarEvent instance is used as a placeholder in the agenda view, if there's
   * no event for that day.
   */
  private boolean mPlaceHolder;
  /**
   * Tells if this BaseCalendarEvent instance is used as a forecast information holder in the agenda
   * view.
   */
  private boolean mWeather;
  /**
   * Duration of the event.
   */
  private String mDuration;
  /**
   * References to a DayItem instance for that event, used to link interaction between the
   * calendar view and the agenda view.
   */
  private IDayItem mDayReference;
  /**
   * References to a WeekItem instance for that event, used to link interaction between the
   * calendar view and the agenda view.
   */
  private IWeekItem mWeekReference;
  /**
   * Weather icon string returned by the Dark Sky API.
   */
  private String mWeatherIcon;
  /**
   * Temperature value returned by the Dark Sky API.
   */
  private double mTemperature;

  protected abstract static class Builder<T extends AbstractBaseCalendarEvent, B extends Builder<T, B>> {
    protected T obj;
    protected B thisObj;

    public Builder() {
      obj = createObj(); thisObj = getThis();
    }

    protected abstract T createObj();
    protected abstract B getThis();

    public T build() { return obj; }
  }

  public int getColor() {
    return mColor;
  }

  public void setColor(int mColor) {
    this.mColor = mColor;
  }

  public String getDescription() {
    return mDescription;
  }

  public boolean isAllDay() {
    return mAllDay;
  }

  public void setAllDay(boolean allDay) {
    this.mAllDay = allDay;
  }

  public void setDescription(String mDescription) {
    this.mDescription = mDescription;
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

  public void setEndTime(Calendar mEndTime) {
    this.mEndTime = mEndTime;
  }

  public void setPlaceholder(boolean placeholder) {
    mPlaceHolder = placeholder;
  }

  public boolean isPlaceholder() {
    return mPlaceHolder;
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

  public void setLocation(String mLocation) {
    this.mLocation = mLocation;
  }

  public Calendar getStartTime() {
    return mStartTime;
  }

  public void setStartTime(Calendar mStartTime) {
    this.mStartTime = mStartTime;
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
    return mPlaceHolder;
  }

  public void setPlaceHolder(boolean mPlaceHolder) {
    this.mPlaceHolder = mPlaceHolder;
  }

  public boolean isWeather() {
    return mWeather;
  }

  public void setWeather(boolean mWeather) {
    this.mWeather = mWeather;
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

  public void setTemperature(double mTemperature) {
    this.mTemperature = mTemperature;
  }

  public CalendarEvent copy() {
    return this;
  }

  @Override public String toString() {
    return "AbstractBaseCalendarEvent{" + "title='" + mTitle + ", instanceDay= " + mInstanceDay.getTime() + "}";
  }
}
