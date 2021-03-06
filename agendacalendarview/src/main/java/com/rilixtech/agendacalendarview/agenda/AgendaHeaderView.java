package com.rilixtech.agendacalendarview.agenda;

import com.rilixtech.agendacalendarview.CalendarManager;
import com.rilixtech.agendacalendarview.R;
import com.rilixtech.agendacalendarview.utils.DateHelper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Header view for the StickyHeaderListView of the agenda view
 */
public class AgendaHeaderView extends LinearLayout {

  public static AgendaHeaderView inflate(ViewGroup parent) {
    return (AgendaHeaderView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.view_agenda_header, parent, false);
  }

  // region Constructors

  public AgendaHeaderView(Context context) {
    super(context);
  }

  public AgendaHeaderView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AgendaHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  // endregion

  // region Public methods

  public void setDay(Calendar day, int currentDayTextColor) {
    TextView tvDayOfMonth = findViewById(R.id.view_agenda_day_of_month);
    TextView tvDayOfWeek = findViewById(R.id.view_agenda_day_of_week);
    View circleView = findViewById(R.id.view_day_circle_selected);

    Calendar today = CalendarManager.getInstance().getToday();

    SimpleDateFormat dayWeekFormatter = new SimpleDateFormat(getContext().getString(R.string.day_name_format),
        CalendarManager.getInstance().getLocale());

    tvDayOfMonth.setTextColor(getResources().getColor(R.color.calendar_text_default));
    tvDayOfWeek.setTextColor(getResources().getColor(R.color.calendar_text_default));

    if (DateHelper.sameDate(day, today)) {
      tvDayOfMonth.setTextColor(currentDayTextColor);
      circleView.setVisibility(VISIBLE);
      GradientDrawable drawable = (GradientDrawable) circleView.getBackground();
      drawable.setStroke((int) (2 * Resources.getSystem().getDisplayMetrics().density), currentDayTextColor);
    } else {
      circleView.setVisibility(INVISIBLE);
    }

    tvDayOfMonth.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));
    tvDayOfWeek.setText(dayWeekFormatter.format(day.getTime()));
  }

  // endregion
}
