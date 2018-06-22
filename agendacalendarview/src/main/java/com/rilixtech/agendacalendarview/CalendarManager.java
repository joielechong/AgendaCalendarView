package com.rilixtech.agendacalendarview;

import android.support.annotation.NonNull;
import com.rilixtech.agendacalendarview.models.BaseCalendarEvent;
import com.rilixtech.agendacalendarview.models.CalendarEvent;
import com.rilixtech.agendacalendarview.models.DayItem;
import com.rilixtech.agendacalendarview.models.IDayItem;
import com.rilixtech.agendacalendarview.models.IWeekItem;
import com.rilixtech.agendacalendarview.models.WeekItem;
import com.rilixtech.agendacalendarview.utils.DateHelper;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This class manages information about the calendar. (Events, weather info...)
 * Holds reference to the days list of the calendar.
 * As the app is using several views, we want to keep everything in one place.
 */
public class CalendarManager {

  private static final String TAG = CalendarManager.class.getSimpleName();

  private static CalendarManager mInstance;

  private Context mContext;
  private Locale mLocale;
  private Calendar mCalendarToday = null;
  private SimpleDateFormat mWeekdayFormatter;
  private SimpleDateFormat mMonthHalfNameFormat;
  private List<Integer> weekends = null;
  private int mWeekendsColor;

  /**
   * List of days used by the calendar
   */
  private List<IDayItem> mDays = new ArrayList<>();
  /**
   * List of weeks used by the calendar
   */
  private List<IWeekItem> mWeeks = new ArrayList<>();
  /**
   * List of events instances
   */
  private List<CalendarEvent> mEvents = new ArrayList<>();

  private CalendarManager(Context context) {
    this.mContext = context;
  }

  public static CalendarManager initInstance(Context context) {
    if (mInstance == null) {
      mInstance = new CalendarManager(context);
    }
    return mInstance;
  }

  public static CalendarManager getInstance() {
    if (mInstance == null) {
      throw new RuntimeException("Please create CalendarManager with initInstance first!");
    }
    return mInstance;
  }

  // endregion

  // region Getters/Setters

  public Locale getLocale() {
    return mLocale;
  }

  //public Context getContext() {
  //  return mContext;
  //}

  public Calendar getToday() {
    if(mCalendarToday == null) mCalendarToday = Calendar.getInstance();
    return mCalendarToday;
  }

  public void setTodayCalendar(Calendar today) {
    this.mCalendarToday = today;
  }

  public List<IWeekItem> getWeeks() {
    return mWeeks;
  }

  public List<CalendarEvent> getEvents() {
    return mEvents;
  }

  public List<IDayItem> getDays() {
    return mDays;
  }

  public SimpleDateFormat getWeekdayFormatter() {
    return mWeekdayFormatter;
  }

  public SimpleDateFormat getMonthHalfNameFormat() {
    return mMonthHalfNameFormat;
  }

  public List<Integer> getWeekends() {
    return weekends;
  }

  public void setWeekends(List<Integer> weekends) {
    this.weekends = weekends;
  }

  public int getWeekendsColor() {
    return mWeekendsColor;
  }

  public void setWeekendsColor(int weekendsColor) {
    this.mWeekendsColor = weekendsColor;
  }

  public void buildCal(@NonNull Calendar minDate, @NonNull Calendar maxDate, @NonNull Locale locale) {
    setLocale(locale);

    mDays.clear();
    mWeeks.clear();
    mEvents.clear();

    // maxDate is exclusive, here we bump back to the previous day, as maxDate if December 1st, 2020,
    // we don't include that month in our list
    maxDate.add(Calendar.MINUTE, -1);

    // Now we iterate between minDate and maxDate so we init our list of weeks
    int maxMonth = maxDate.get(Calendar.MONTH);
    int maxYear = maxDate.get(Calendar.YEAR);

    int currentMonth = minDate.get(Calendar.MONTH);
    int currentYear = minDate.get(Calendar.YEAR);

    // Clean week to be copied when creating a new week.
    IWeekItem cleanWeek = new WeekItem();

    while ((currentMonth <= maxMonth // Up to, including the month.
        || currentYear < maxYear) // Up to the year.
        && currentYear < maxYear + 1) { // But not > next yr.


      IWeekItem weekItem = generateWeek(minDate, cleanWeek, currentYear, currentMonth);
      mWeeks.add(weekItem);

      Log.d(TAG, String.format("Adding week: %s", weekItem));

      minDate.add(Calendar.WEEK_OF_YEAR, 1);

      currentMonth = minDate.get(Calendar.MONTH);
      currentYear = minDate.get(Calendar.YEAR);
    }
  }

  /**
   * Generate week item by month and year
   * @param calendar calendar of the week
   * @param cleanWeek an empty week item to be cloned
   * @param year year of the week
   * @param month month of the week
   * @return week item
   */
  private IWeekItem generateWeek(Calendar calendar, IWeekItem cleanWeek, int year, int month) {
    Date date = calendar.getTime();
    int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
    IWeekItem weekItem = cleanWeek.copy();
    weekItem.setWeekInYear(weekOfYear);
    weekItem.setYear(year);
    weekItem.setDate(date);
    weekItem.setMonth(month);
    weekItem.setLabel(mMonthHalfNameFormat.format(date));
    List<IDayItem> dayItems = getDayCells(calendar); // gather days for the built week
    weekItem.setDayItems(dayItems);

    return weekItem;
  }

  public void loadEvents(List<CalendarEvent> eventList) {
    CalendarEvent noEvent;
    if(eventList.size() == 0) {
      noEvent = new BaseCalendarEvent();
    } else {
      noEvent = eventList.get(0);
    }
    for (IWeekItem weekItem : getWeeks()) {
      for (int i = 0; i < weekItem.getDayItems().size(); i++) {
        DayItem dayItem = (DayItem) weekItem.getDayItems().get(i);
        boolean isEventForDay = false;
        for (CalendarEvent event : eventList) {
          if (DateHelper.isBetweenInclusive(dayItem.getDate(), event.getStartTime(),
              event.getEndTime())) {
            CalendarEvent copy = event.copy();

            Calendar dayInstance = Calendar.getInstance();
            dayInstance.setTime(dayItem.getDate());
            copy.setInstanceDay(dayInstance);
            copy.setDayReference(dayItem);
            copy.setWeekReference(weekItem);
            // add instances in chronological order
            getEvents().add(copy);
            isEventForDay = true;

            dayItem.setEventTotal(dayItem.getEventTotal() + 1);

            // check if day is a weekend
            int dayOfWeek = dayInstance.get(Calendar.DAY_OF_WEEK);
            if(weekends != null) {
              dayItem.setWeekend(weekends.contains(dayOfWeek));
            }
          }
        }
        if (!isEventForDay) {
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(dayItem.getDate());

          // check if day is a weekend
          int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
          if(weekends != null) {
            dayItem.setWeekend(weekends.contains(dayOfWeek));
          }

          CalendarEvent copy = noEvent.copy();

          copy.setInstanceDay(calendar);
          copy.setDayReference(dayItem);
          copy.setWeekReference(weekItem);
          copy.setLocation("");
          copy.setTitle(mContext.getString(R.string.agenda_event_no_events));
          copy.setPlaceholder(true);
          getEvents().add(copy);
        }
      }
    }
  }

  public void loadCal(Locale locale, List<IWeekItem> weekItems, List<IDayItem> dayItems, List<CalendarEvent> calendarEvents) {
    mWeeks = weekItems;
    mDays = dayItems;
    mEvents = calendarEvents;
    setLocale(locale);
  }

  private Calendar mCalendar;
  private List<IDayItem> getDayCells(Calendar startCal) {
    if (mCalendar == null) mCalendar = Calendar.getInstance(mLocale);
    mCalendar.setTime(startCal.getTime());
    List<IDayItem> dayItems = new ArrayList<>();

    int firstDayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
    int offset = mCalendar.getFirstDayOfWeek() - firstDayOfWeek;
    if (offset > 0) {
      offset -= 7;
    }
    mCalendar.add(Calendar.DATE, offset);

    IDayItem cleanDay = new DayItem();
    Log.d(TAG, String.format("Buiding row week starting at %s", mCalendar.getTime()));
    for (int c = 0; c < 7; c++) {
      IDayItem dayItem = cleanDay.copy();
      dayItem.buildDayItemFromCal(mCalendar);
      //dayItem.setEventTotal(eventPerDay(dayItem));
      dayItems.add(dayItem);
      mCalendar.add(Calendar.DATE, 1);
    }

    mDays.addAll(dayItems);
    return dayItems;
  }

  private void setLocale(Locale locale) {
    this.mLocale = locale;
    this.mCalendarToday = Calendar.getInstance(locale);
    mWeekdayFormatter = new SimpleDateFormat(mContext.getString(R.string.day_name_format), locale);
    mMonthHalfNameFormat = new SimpleDateFormat(mContext.getString(R.string.month_half_name_format), locale);
  }
}
