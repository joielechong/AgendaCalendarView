package com.rilixtech.sample;

import android.widget.TextView;
import com.rilixtech.agendacalendarview.AgendaCalendarView;
import com.rilixtech.agendacalendarview.CalendarPickerController;
import com.rilixtech.agendacalendarview.models.BaseCalendarEvent;
import com.rilixtech.agendacalendarview.models.CalendarEvent;
import com.rilixtech.agendacalendarview.models.IDayItem;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CalendarPickerController {

  private static final String LOG_TAG = MainActivity.class.getSimpleName();

  private Toolbar mToolbar;
  private AgendaCalendarView mAgendaCalendarView;
  private TextView mTvDate;

  // region Lifecycle methods

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mToolbar = findViewById(R.id.activity_toolbar);
    mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);
    mTvDate = findViewById(R.id.main_date_tv);

    setSupportActionBar(mToolbar);
    getSupportActionBar().setTitle("Agenda");
    mToolbar.setTitle("Agenda");

    // minimum and maximum date of our calendar
    // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
    Calendar minDate = Calendar.getInstance();
    Calendar maxDate = Calendar.getInstance();

    minDate.add(Calendar.MONTH, -2);
    minDate.set(Calendar.DAY_OF_MONTH, 1);
    maxDate.add(Calendar.YEAR, 1);

    List<CalendarEvent> eventList = new ArrayList<>();
    mockList(eventList);

    List<Integer> weekends = new ArrayList<>();
    weekends.add(Calendar.SUNDAY);
    mAgendaCalendarView.setMinimumDate(minDate)
        .setMaximumDate(maxDate)
        .setCalendarEvents(eventList)
        .setLocale(Locale.getDefault())
        //.setEventRender(new DrawableEventRenderer())
        .setCalendarPickerController(this)
        .setWeekendsColor(getResources().getColor(android.R.color.background_dark))
        .setWeekends(weekends)
        .build();
  }

  @Override public void onDaySelected(IDayItem dayItem) {
    Log.d(LOG_TAG, String.format("Selected day: %s", dayItem));
  }

  @Override public void onEventSelected(CalendarEvent event) {
    Log.d(LOG_TAG, String.format("Selected event: %s", event));
  }

  @Override public void onScrollToDate(Calendar calendar) {
    //if (getSupportActionBar() != null) {
      mTvDate.setText(
          calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
              + " "
              + calendar.get(Calendar.YEAR));
    //}
  }

  private void mockList(List<CalendarEvent> eventList) {
    Calendar startTime1 = Calendar.getInstance();
    Calendar endTime1 = Calendar.getInstance();
    endTime1.add(Calendar.MONTH, 1);
    DrawableCalendarEvent event1 = new DrawableCalendarEvent.Builder().title("Revisión requerimientos")
        .description("Diseño App")
        .location("Despacho 1")
        .color(ContextCompat.getColor(this, R.color.theme_event_pending))
        .startTime(startTime1)
        .endTime(endTime1)
        .drawableId(R.drawable.ic_launcher)
        .allDay(true).build();

    eventList.add(event1);

    Calendar startTime2 = Calendar.getInstance();
    startTime2.add(Calendar.DAY_OF_YEAR, 1);
    Calendar endTime2 = Calendar.getInstance();
    endTime2.add(Calendar.DAY_OF_YEAR, 3);


    DrawableCalendarEvent event2 = new DrawableCalendarEvent.Builder()
        .title("Arquitectura, reunión seguimiento")
        .description("Reunión periodica importante")
        .location("Sala 2B")
        .color(ContextCompat.getColor(this, R.color.theme_event_pending))
        .startTime(startTime2)
        .endTime(endTime2)
        .drawableId(R.drawable.ic_launcher)
        .allDay(true).build();
    eventList.add(event2);

    Calendar startTime3 = Calendar.getInstance();
    Calendar endTime3 = Calendar.getInstance();
    startTime3.set(Calendar.HOUR_OF_DAY, 14);
    startTime3.set(Calendar.MINUTE, 0);
    endTime3.set(Calendar.HOUR_OF_DAY, 15);
    endTime3.set(Calendar.MINUTE, 0);

    DrawableCalendarEvent event3 = new DrawableCalendarEvent.Builder()
        .title("14:00 - 15:00 Reunión de coordinación AGN")
        .description("i")
        .location("Despacho 2")
        .color(ContextCompat.getColor(this, R.color.theme_event_confirmed))
        .startTime(startTime3)
        .endTime(endTime3)
        .drawableId(R.drawable.ic_launcher)
        .allDay(false).build();
    eventList.add(event3);

    Calendar startTime4 = Calendar.getInstance();
    Calendar endTime4 = Calendar.getInstance();
    startTime4.set(Calendar.HOUR_OF_DAY, 16);
    startTime4.set(Calendar.MINUTE, 0);
    endTime4.set(Calendar.HOUR_OF_DAY, 17);
    endTime4.set(Calendar.MINUTE, 0);

    DrawableCalendarEvent event4 = new DrawableCalendarEvent.Builder()
        .title("16:00 - 17:00 Reunión de coordinación AGN 2")
        .description("i")
        .location("Despacho 3")
        .color(ContextCompat.getColor(this, R.color.theme_event_confirmed))
        .startTime(startTime4)
        .endTime(endTime4)
        .drawableId(R.drawable.ic_launcher)
        .allDay(false).build();

    eventList.add(event4);

    Calendar startTime5 = Calendar.getInstance();
    Calendar endTime5 = Calendar.getInstance();
    startTime5.set(Calendar.HOUR_OF_DAY, 18);
    startTime5.set(Calendar.MINUTE, 0);
    endTime5.set(Calendar.HOUR_OF_DAY, 19);
    endTime5.set(Calendar.MINUTE, 0);

    DrawableCalendarEvent event5 = new DrawableCalendarEvent.Builder()
        .title("16:00 - 17:00 Reunión de coordinación AGN 3")
        .description("i")
        .location("Despacho 3")
        .color(ContextCompat.getColor(this, R.color.theme_event_confirmed))
        .startTime(startTime5)
        .endTime(endTime5)
        .drawableId(R.drawable.ic_launcher)
        .allDay(false).build();

    eventList.add(event5);

    Calendar startTime6 = Calendar.getInstance();
    Calendar endTime6 = Calendar.getInstance();
    endTime6.add(Calendar.YEAR, 1);
    DrawableCalendarEvent event6 = new DrawableCalendarEvent.Builder().title("Revisión requerimientos")
        .description("Diseño App")
        .location("Despacho 1")
        .color(ContextCompat.getColor(this, R.color.theme_event_pending))
        .startTime(startTime6)
        .endTime(endTime6)
        .drawableId(R.drawable.ic_launcher)
        .allDay(true).build();

    eventList.add(event6);
  }

  // endregion
}
